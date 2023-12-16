package com.ssu.micropower.models.domain

import java.sql.Timestamp

data class WeatherCurrent(
    val time: Timestamp,
    val temperature: Float,
    val precipitation: Float,
    val weatherCode: Int,
    val pressure: Float,
    val windSpeed: Float,
    val windDirection: Int
)