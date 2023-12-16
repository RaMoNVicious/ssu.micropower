package com.ssu.micropower.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.micropower.api.getMessages
import com.ssu.micropower.api.onFailure
import com.ssu.micropower.api.onSuccess
import com.ssu.micropower.models.domain.Location
import com.ssu.micropower.usecases.ILocationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val locationUseCase: ILocationUseCase,
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> = _locations

    fun getLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            locationUseCase
                .getLocationsLocal()
                .collect { result ->
                    result
                        .onSuccess { _locations.postValue(it) }
                        .onFailure { _message.postValue(it.getMessages()) }
                }
        }
    }
}