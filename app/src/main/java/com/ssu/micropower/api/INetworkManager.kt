package com.ssu.micropower.api

import androidx.lifecycle.LiveData

interface INetworkManager {
    fun isNetworkAvailable(): Boolean

    fun hasConnection(): LiveData<Boolean>
}