package com.ssu.micropower.repositories

import com.ssu.micropower.api.Result
import com.ssu.micropower.models.api.WeatherDto
import com.ssu.micropower.models.domain.Coordinates

interface IWeatherRepository {

    suspend fun getWeather(coordinates: Coordinates): Result<WeatherDto>
}