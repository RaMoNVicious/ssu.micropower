package com.ssu.micropower.models

import com.ssu.micropower.app.Constants
import com.ssu.micropower.models.api.WeatherDto
import com.ssu.micropower.models.api.WeatherHourlyDto
import com.ssu.micropower.models.dao.WeatherCurrentEntity
import com.ssu.micropower.models.dao.WeatherEntity
import com.ssu.micropower.models.domain.Coordinates
import com.ssu.micropower.models.domain.WeatherCurrent
import com.ssu.micropower.models.domain.Weather
import com.ssu.micropower.models.domain.WeatherHourly
import java.sql.Timestamp
import java.util.Date

fun WeatherEntity.toDomain(): Weather {
    return Weather(
        locationId = this.locationId,
        coordinates = Coordinates(
            latitude = this.latitude,
            longitude = this.longitude
        ),
        current = WeatherCurrent(
            time = Timestamp(this.current.time),
            temperature = this.current.temperature,
            precipitation = this.current.precipitation,
            weatherCode = this.current.weatherCode,
            pressure = this.current.pressure,
            windSpeed = this.current.windSpeed,
            windDirection = this.current.windDirection
        ),
        hourly = listOf()
    )
}

fun Weather.toDao(): WeatherEntity {
    return WeatherEntity(
        locationId = this.locationId,
        latitude = this.coordinates.latitude,
        longitude = this.coordinates.longitude,
        current = WeatherCurrentEntity(
            time = this.current.time.time,
            temperature = this.current.temperature,
            precipitation = this.current.precipitation,
            weatherCode = this.current.weatherCode,
            pressure = this.current.pressure,
            windSpeed = this.current.windSpeed,
            windDirection = this.current.windDirection
        ),
    )
}

fun WeatherDto.toDomain(locationId: Long): Weather {
    return Weather(
        locationId = locationId,
        coordinates = Coordinates(
            latitude = this.latitude,
            longitude = this.longitude
        ),
        current = WeatherCurrent(
            time = Timestamp(this.current.time),
            temperature = this.current.temperature,
            precipitation = this.current.precipitation,
            weatherCode = this.current.weatherCode,
            pressure = this.current.pressure,
            windSpeed = this.current.windSpeed,
            windDirection = this.current.windDirection
        ),
        hourly = this.hourly.toDomain()
    )
}

fun WeatherHourlyDto.toDomain(): List<WeatherHourly> {
    val max = listOf(
        this.time,
        this.precipitationProbability,
        this.precipitation,
        this.rain,
        this.pressure,
        this.windSpeed,
        this.windDirection,
    ).maxOfOrNull {
        it.size
    } ?: 0

    return (0..<max)
        .asSequence()
        .map { i ->
            object {
                val date = this@toDomain.time[i]
                val temperature = this@toDomain.temperature[i]
                val precipitationProbability = this@toDomain.precipitationProbability[i]
                val precipitation = this@toDomain.precipitation[i]
                val rain = this@toDomain.rain[i]
                val pressure = this@toDomain.pressure[i]
                val windSpeed = this@toDomain.windSpeed[i]
                val windDirection = this@toDomain.windDirection[i]
            }
        }
        .chunked(24)
        .drop(1)
        .take(Constants.WEATHER_ITEMS_COUNT)
        .map { day ->
            WeatherHourly(
                time = Date(1000 * day.sumOf { it.date } / day.size),
                temperatureMin = day.minOf { it.temperature },
                temperatureMax = day.maxOf { it.temperature },
                precipitationProbability = day.sumOf { it.precipitationProbability } / day.size,
                precipitation = (day.sumOf { it.precipitation.toDouble() } / day.size).toFloat(),
                rain = (day.sumOf { it.rain.toDouble() } / day.size).toFloat(),
                pressure = (day.sumOf { it.pressure.toDouble() } / day.size).toFloat(),
                windSpeed = (day.sumOf { it.windSpeed.toDouble() } / day.size).toFloat(),
                windDirection = day.sumOf { it.windDirection } / day.size
            )
        }
        .toList()
}