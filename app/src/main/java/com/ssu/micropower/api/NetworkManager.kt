package com.ssu.micropower.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.ssu.micropower.models.api.BaseResponse
import retrofit2.Response

class NetworkManager(private val context: Context) : INetworkManager {
    private val hasConnection = MutableLiveData<Boolean>()

    override fun hasConnection(): LiveData<Boolean> {
        return hasConnection
    }

    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    init {
        val connectivityManager = context
            .getSystemService(ConnectivityManager::class.java) as ConnectivityManager

        connectivityManager.requestNetwork(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)

                    val unmetered = networkCapabilities
                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                    hasConnection.postValue(unmetered)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    hasConnection.postValue(false)
                }
            }
        )
    }

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.connectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
}

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

suspend fun <T> INetworkManager.consumeRequest(request: suspend () -> Response<BaseResponse<T>>): Result<T> {
    return try {
        val result = request()
        when {
            result.isSuccessful -> Result.Success(result.body()!!.data)
            result.isSuccessful.not() ->
                Result.Failure(
                    Failure.ServerError(
                        result
                            .errorBody()
                            ?.let {
                                Gson().fromJson(it.string(), BaseResponse::class.java)
                            }
                            ?.messages
                            ?.joinToString(separator = "\n") ?: "EMPTY"
                    )
                )

            isNetworkAvailable().not() -> Result.Failure(Failure.NoConnection)
            else -> Result.Failure(Failure.Unknown())
        }
    } catch (ex: Exception) {
        Result.Failure(Failure.Unknown(messages = ex.message ?: ex.stackTraceToString()))
    }
}

suspend fun <T> INetworkManager.consumeWeatherRequest(request: suspend () -> Response<T>): Result<T> {
    return try {
        val result = request()
        when {
            result.isSuccessful -> Result.Success(result.body()!!)
            result.isSuccessful.not() -> Result.Failure(Failure.ServerError(result.message()))
            isNetworkAvailable().not() -> Result.Failure(Failure.NoConnection)
            else -> Result.Failure(Failure.Unknown())
        }
    } catch (ex: Exception) {
        Result.Failure(Failure.Unknown(messages = ex.message ?: ex.stackTraceToString()))
    }
}
