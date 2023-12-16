package com.ssu.micropower.repositories

import com.ssu.micropower.api.IAuthService
import com.ssu.micropower.api.INetworkManager
import com.ssu.micropower.api.Result
import com.ssu.micropower.api.consumeRequest
import com.ssu.micropower.models.api.UserDto
import com.ssu.micropower.models.api.UserLoginDto
import com.ssu.micropower.models.api.UserRefreshDto
import com.ssu.micropower.models.api.UserSessionRefreshDto
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authService: IAuthService,
    private val networkManager: INetworkManager,
) : IUserRepository {

    override suspend fun auth(login: String, password: String): Result<UserDto> {
        return networkManager.consumeRequest { authService.auth(UserLoginDto(login, password)) }
    }

    override suspend fun refresh(sessionId: Int, refreshToken: String): Result<UserSessionRefreshDto> {
        return networkManager.consumeRequest {
            authService.refresh(
                sessionId,
                UserRefreshDto(refreshToken)
            )
        }
    }

    override suspend fun logout(sessionId: String): Result<String> {
        return networkManager.consumeRequest {
            authService.logout(sessionId)
        }
    }
}
