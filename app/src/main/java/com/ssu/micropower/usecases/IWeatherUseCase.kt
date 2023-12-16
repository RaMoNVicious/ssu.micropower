package com.ssu.micropower.usecases

import com.ssu.micropower.models.domain.Coordinates
import com.ssu.micropower.models.domain.Weather
import kotlinx.coroutines.flow.Flow

interface IWeatherUseCase {

    suspend fun getWeather(locationId: Long, coordinates: Coordinates): Flow<Result<Weather>>

}