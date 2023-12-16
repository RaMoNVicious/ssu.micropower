package com.ssu.micropower.repositories

import com.ssu.micropower.api.INetworkManager
import com.ssu.micropower.api.IWeatherService
import com.ssu.micropower.api.Result
import com.ssu.micropower.api.consumeWeatherRequest
import com.ssu.micropower.models.api.WeatherDto
import com.ssu.micropower.models.domain.Coordinates
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: IWeatherService,
    private val networkManager: INetworkManager,
) : IWeatherRepository {

    override suspend fun getWeather(coordinates: Coordinates) : Result<WeatherDto> {
        return networkManager.consumeWeatherRequest {
            weatherService.getWeather(
                latitude = coordinates.latitude,
                longitude = coordinates.longitude
            )
        }
    }


}