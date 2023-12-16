package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class WeatherCurrentDto(
    val time: Long,
    @SerializedName("temperature_2m")
    val temperature: Float,
    val precipitation: Float,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("surface_pressure")
    val pressure: Float,
    @SerializedName("wind_speed_10m")
    val windSpeed: Float,
    @SerializedName("wind_direction_10m")
    val windDirection: Int
)