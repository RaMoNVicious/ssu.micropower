package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class WeatherHourlyDto(
    val time: List<Long>,
    @SerializedName("temperature_2m")
    val temperature: List<Float>,
    @SerializedName("precipitation_probability")
    val precipitationProbability: List<Int>,
    val precipitation: List<Float>,
    val rain: List<Float>,
    @SerializedName("surface_pressure")
    val pressure: List<Float>,
    @SerializedName("wind_speed_10m")
    val windSpeed: List<Float>,
    @SerializedName("wind_direction_10m")
    val windDirection: List<Int>
)