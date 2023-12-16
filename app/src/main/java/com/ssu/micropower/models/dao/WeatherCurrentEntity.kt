package com.ssu.micropower.models.dao

import androidx.room.ColumnInfo

data class WeatherCurrentEntity(
    val time: Long,
    val temperature: Float,
    val precipitation: Float,
    @ColumnInfo(name = "weather_code")
    val weatherCode: Int,
    val pressure: Float,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Float,
    @ColumnInfo(name = "wind_direction")
    val windDirection: Int
)
