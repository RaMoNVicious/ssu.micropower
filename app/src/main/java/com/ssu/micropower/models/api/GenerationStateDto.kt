package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class GenerationStateDto(
    @SerializedName("current_system_state_data")
    val systemState: SystemStateDto
)

data class SystemStateDto(
    val time: String,
    val consumption: Float,
    @SerializedName("fact_sales")
    val sell: Float,
    @SerializedName("purchase")
    val purchase: Float
)
