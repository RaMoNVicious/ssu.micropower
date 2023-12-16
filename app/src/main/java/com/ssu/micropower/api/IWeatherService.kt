package com.ssu.micropower.api

import com.ssu.micropower.app.Constants
import com.ssu.micropower.models.api.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface IWeatherService {

    @GET/*("forecast")*/
    suspend fun getWeather(
        @Url baseUrl: String = Constants.WEATHER_URL,
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("current") temperature: String = "temperature_2m,precipitation,weather_code,surface_pressure,wind_speed_10m,wind_direction_10m",
        @Query("hourly") hourly: String = "temperature_2m,precipitation_probability,precipitation,rain,surface_pressure,wind_speed_10m,wind_direction_10m",
        @Query("wind_speed_unit") windSpeedUnits: String = "ms",
        @Query("timeformat") timeFormat: String = "unixtime",
    ): Response<WeatherDto>
}