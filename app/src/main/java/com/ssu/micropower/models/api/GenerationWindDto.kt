package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class GenerationWindDto(
    @SerializedName("actual_wind_generation_data")
    val dataCurrent: GenerationWindCurrentDto,
    @SerializedName("forecast_wind_generation_data")
    val dataForecast: GenerationWindForecastDto,
    @SerializedName("max_power")
    val maxPower: Int
)

data class GenerationWindCurrentDto(
    @SerializedName("actual_wind_generation")
    val generation: Float,
    val time: String,
    @SerializedName("wind_speed")
    val windSpeed: Float
)

data class GenerationWindForecastDto(
    @SerializedName("forecast_wind_generation")
    val generation: Float,
    val time: String,
    @SerializedName("wind_speed")
    val windSpeed: Float
)