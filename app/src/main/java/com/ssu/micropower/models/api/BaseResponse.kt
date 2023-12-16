package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("statusCode")
    val statusCode: Int,
    val success: Boolean,
    val messages: List<String>,
    val data: T
)
