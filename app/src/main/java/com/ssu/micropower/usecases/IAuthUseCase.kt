package com.ssu.micropower.usecases

import com.ssu.micropower.api.Result
import com.ssu.micropower.models.domain.User
import com.ssu.micropower.models.domain.UserSessionRefresh
import kotlinx.coroutines.flow.Flow

interface IAuthUseCase {

    suspend fun refresh(): Flow<Result<User>>

    suspend fun auth(login: String, password: String): Flow<Result<User>>

    suspend fun logout(): Flow<Result<Boolean>>
}