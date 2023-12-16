package com.ssu.micropower.usecases

import com.ssu.micropower.api.onFailure
import com.ssu.micropower.api.onSuccess
import com.ssu.micropower.models.dao.IWeatherDao
import com.ssu.micropower.models.domain.Coordinates
import com.ssu.micropower.models.domain.Weather
import com.ssu.micropower.models.toDao
import com.ssu.micropower.models.toDomain
import com.ssu.micropower.repositories.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository,
    private val weatherDao: IWeatherDao
) : IWeatherUseCase {

    override suspend fun getWeather(
        locationId: Long,
        coordinates: Coordinates
    ): Flow<Result<Weather>> {
        return flow {
            weatherRepository
                .getWeather(coordinates)
                .onSuccess {
                    val weather = it.toDomain(locationId)
                    weatherDao.delete(locationId)
                    weatherDao.insert(weather.toDao())
                    emit(Result.success(weather))
                }
                .onFailure {
                    val weather = weatherDao.getWeather(locationId)

                    if (weather.isEmpty()) {
                        emit(Result.failure(it))
                    } else {
                        emit(Result.success(weather.first().toDomain()))
                    }
                }
        }
    }
}

