package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class GenerationSolarDto(
    @SerializedName("actual_solar_generation_data")
    val dataCurrent: GenerationSolarCurrentDto,
    @SerializedName("forecast_solar_generation_data")
    val dataForecast: GenerationSolarForecastDto,
    @SerializedName("max_power")
    val maxPower: Int
)

data class GenerationSolarCurrentDto(
    @SerializedName("actual_solar_generation")
    val generation: Float,
    val time: String,
    val clouds: Float
)

data class GenerationSolarForecastDto(
    @SerializedName("forecast_solar_generation")
    val generation: Float,
    val time: String,
    val clouds: Float
)
