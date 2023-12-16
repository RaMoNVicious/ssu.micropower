package com.ssu.micropower.models.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherHourlyEntity(
    @PrimaryKey
    val time: Long,
    @ColumnInfo(name = "temperature_min")
    val temperatureMin: Float,
    @ColumnInfo(name = "temperature_max")
    val temperatureMax: Float,
    @ColumnInfo(name = "precipitation_probability")
    val precipitationProbability: Int,
    val precipitation: Float,
    val rain: Float,
    val pressure: Float,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Float,
    @ColumnInfo(name = "wind_direction")
    val windDirection: Int
)