package com.ssu.micropower.models.domain

data class UserSessionRefresh(
    val accessToken: String,
    val accessTokenExpiry: Int,
    val refreshToken: String,
    val refreshTokenExpiry: Int
)