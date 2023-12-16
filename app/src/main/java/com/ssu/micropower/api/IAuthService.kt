package com.ssu.micropower.api

import com.ssu.micropower.models.api.BaseResponse
import com.ssu.micropower.models.api.UserLoginDto
import com.ssu.micropower.models.api.UserDto
import com.ssu.micropower.models.api.UserRefreshDto
import com.ssu.micropower.models.api.UserSessionRefreshDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface IAuthService {

    @Headers("Content-Type: application/json")
    @POST("sessions")
    suspend fun auth(@Body auth: UserLoginDto): Response<BaseResponse<UserDto>>

    @Headers("Content-Type: application/json")
    @PATCH("sessions/{sessionId}")
    suspend fun refresh(
        @Path("sessionId") sessionId: Int,
        @Body refreshToken: UserRefreshDto
    ): Response<BaseResponse<UserSessionRefreshDto>>

    @DELETE("sessions/{sessionId}")
    suspend fun logout(
        @Path("sessionId") sessionId: String
    ): Response<BaseResponse<String>>
}