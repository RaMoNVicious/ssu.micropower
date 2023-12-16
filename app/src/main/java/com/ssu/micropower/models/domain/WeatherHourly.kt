package com.ssu.micropower.models.domain

import java.util.Date

data class WeatherHourly(
    val time: Date,
    val temperatureMin: Float,
    val temperatureMax: Float,
    val precipitationProbability: Int,
    val precipitation: Float,
    val rain: Float,
    val pressure: Float,
    val windSpeed: Float,
    val windDirection: Int
)