package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class LocationDeviceDto(
    @SerializedName("dev_id")
    val id: Long,
    @SerializedName("dev_name")
    val name: String,
    @SerializedName("dev_cnt")
    val count: Int
)
