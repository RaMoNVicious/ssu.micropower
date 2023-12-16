package com.ssu.micropower.models.domain

data class Weather(
    val locationId: Long,
    val coordinates: Coordinates,
    val current: WeatherCurrent,
    val hourly: List<WeatherHourly>
)