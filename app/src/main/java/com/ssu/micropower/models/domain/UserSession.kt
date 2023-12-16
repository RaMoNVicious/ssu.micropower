package com.ssu.micropower.models.domain

data class UserSession(
    val sessionId: Int,
    val accessToken: String,
    val accessTokenExpiry: String,
    val refreshToken: String,
    val refreshTokenExpiry: String
)
