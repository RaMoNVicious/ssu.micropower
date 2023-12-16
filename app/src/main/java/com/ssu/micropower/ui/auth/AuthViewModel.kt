package com.ssu.micropower.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssu.micropower.api.Failure
import com.ssu.micropower.api.getMessages
import com.ssu.micropower.api.onFailure
import com.ssu.micropower.api.onSuccess
import com.ssu.micropower.models.domain.User
import com.ssu.micropower.usecases.IAuthUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authUseCase: IAuthUseCase,
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun auth() {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            authUseCase
                .refresh()
                .collect { result ->
                    result
                        .onSuccess { _user.postValue(it) }
                        .onFailure {
                            when (it) {
                                is Failure.AuthNeeded -> { /* do nothing */ }
                                else -> _message.postValue(it.getMessages())
                            }
                        }
                }
        }
    }

    fun auth(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            authUseCase
                .auth(login, password)
                .collect { result ->
                    result
                        .onSuccess { _user.postValue(it) }
                        .onFailure { _message.postValue(it.getMessages()) }
                }
        }
    }
}
