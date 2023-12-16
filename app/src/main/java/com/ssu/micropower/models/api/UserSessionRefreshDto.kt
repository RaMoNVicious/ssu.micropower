package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class UserSessionRefreshDto(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("access_token_expires_in")
    val accessTokenExpiry: Int,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("refresh_token_expires_in")
    val refreshTokenExpiry: Int
)