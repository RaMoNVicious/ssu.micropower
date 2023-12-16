package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class UserRefreshDto(
    @SerializedName("refresh_token")
    val refreshToken: String
)
