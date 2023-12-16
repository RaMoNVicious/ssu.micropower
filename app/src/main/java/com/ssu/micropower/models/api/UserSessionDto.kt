package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class UserSessionDto(
    @SerializedName("session_id")
    val sessionId: Int,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("access_token_expiry")
    val accessTokenExpiry: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("refresh_token_expiry")
    val refreshTokenExpiry: String
)