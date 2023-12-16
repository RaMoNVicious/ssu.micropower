package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    val latitude: Float,
    val longitude: Float,
    @SerializedName("current")
    val current: WeatherCurrentDto,
    val hourly: WeatherHourlyDto
)
