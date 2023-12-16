package com.ssu.micropower.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.micropower.api.Result
import com.ssu.micropower.api.getMessages
import com.ssu.micropower.api.onFailure
import com.ssu.micropower.api.onSuccess
import com.ssu.micropower.models.api.GenerationBatteryDto
import com.ssu.micropower.models.api.GenerationSolarDto
import com.ssu.micropower.models.api.GenerationStateDto
import com.ssu.micropower.models.api.GenerationWindDto
import com.ssu.micropower.models.domain.GenerationItem
import com.ssu.micropower.models.domain.Location
import com.ssu.micropower.models.domain.LocationInfo
import com.ssu.micropower.models.domain.LocationStructure
import com.ssu.micropower.models.domain.Weather
import com.ssu.micropower.models.toDate
import com.ssu.micropower.models.toGenerationItem
import com.ssu.micropower.models.toGenerationItemDevice
import com.ssu.micropower.usecases.IAuthUseCase
import com.ssu.micropower.usecases.ILocationUseCase
import com.ssu.micropower.usecases.IWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val authUseCase: IAuthUseCase,
    private val locationUseCase: ILocationUseCase,
    private val weatherUseCase: IWeatherUseCase
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> = _locations

    private val _locationInfo = MutableLiveData<LocationInfo>()
    val locationInfo: LiveData<LocationInfo> = _locationInfo

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    private val _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    fun getLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            locationUseCase
                .getLocations()
                .collect { result ->
                    result
                        .onSuccess { _locations.postValue(it) }
                        .onFailure { _message.postValue(it.getMessages()) }
                }
        }
    }

    fun getLocationStructure(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            val (resA, resB) = awaitAll(
                async { locationUseCase.getStructure(location.id) },
                async { locationUseCase.getComponents(location.id) }
            )
            resA.combine(resB) { structureResult, componentsResult ->
                when {
                    structureResult is Result.Success && componentsResult is Result.Success -> {
                        val structure = structureResult.value as LocationStructure

                        val items = (componentsResult.value as List<*>)
                            .filter { it is GenerationSolarDto || it is GenerationBatteryDto || it is GenerationWindDto }
                            .map { component ->
                                when (component) {
                                    is GenerationSolarDto -> component.toGenerationItem(
                                        structure.solarPanels.map { it.toGenerationItemDevice() }
                                    )

                                    is GenerationWindDto -> component.toGenerationItem(
                                        structure.windMills.map { it.toGenerationItemDevice() }
                                    )

                                    is GenerationBatteryDto -> component.toGenerationItem(
                                        structure.accumulators.map { it.toGenerationItemDevice() }
                                    )

                                    else -> {}
                                }
                            } as List<GenerationItem>

                        val status = componentsResult.value
                            .find { it is GenerationStateDto } as GenerationStateDto

                        _locationInfo.postValue(
                            LocationInfo(
                                location = location,
                                time = status.systemState.time.toDate(),
                                consumption = status.systemState.consumption,
                                sell = status.systemState.sell,
                                purchase = status.systemState.purchase,
                                generation = items
                            )
                        )
                    }

                    else -> {
                        structureResult.onFailure { _message.postValue(it.getMessages()) }
                        componentsResult.onFailure { _message.postValue(it.getMessages()) }
                    }
                }
            }.collect()
        }
    }

    fun getWeather(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            weatherUseCase
                .getWeather(location.id, location.coordinates)
                .collect { result ->
                    result
                        .onSuccess { _weather.postValue(it) }
                        .onFailure { _message.postValue(it.getMessages()) }
                }

        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            authUseCase
                .logout()
                .collect { result ->
                    result
                        .onSuccess { _logout.postValue(it) }
                        .onFailure { _message.postValue(it.getMessages()) }
                }

        }
    }
}