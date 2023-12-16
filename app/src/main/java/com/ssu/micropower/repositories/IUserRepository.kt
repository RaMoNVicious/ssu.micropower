package com.ssu.micropower.repositories

import com.ssu.micropower.api.Result
import com.ssu.micropower.models.api.UserDto
import com.ssu.micropower.models.api.UserSessionRefreshDto

interface IUserRepository {
    suspend fun auth(login: String, password: String): Result<UserDto>

    suspend fun refresh(sessionId: Int, refreshToken: String): Result<UserSessionRefreshDto>

    suspend fun logout(sessionId: String): Result<String>
}