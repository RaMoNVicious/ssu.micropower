package com.ssu.micropower.models

import com.ssu.micropower.models.api.LocationDto
import com.ssu.micropower.models.api.LocationStructureDto
import com.ssu.micropower.models.dao.LocationCityEntity
import com.ssu.micropower.models.dao.LocationCountryEntity
import com.ssu.micropower.models.dao.LocationEntity
import com.ssu.micropower.models.domain.Coordinates
import com.ssu.micropower.models.domain.Location
import com.ssu.micropower.models.domain.LocationCity
import com.ssu.micropower.models.domain.LocationCountry
import com.ssu.micropower.models.domain.LocationDevice
import com.ssu.micropower.models.domain.LocationStructure

fun LocationDto.toDomain() : Location {
    return Location(
        id = this.locationId,
        coordinates = Coordinates(
            latitude = this.latitude,
            longitude = this.longitude
        ),
        name = this.name,
        typeName = this.typeName,
        city = LocationCity(
            name = this.cityName,
            timeZone = this.cityTimeZone
        ),
        country = LocationCountry(
            name = this.countryName
        )
    )
}

fun Location.toDao() : LocationEntity {
    return LocationEntity(
        id = this.id,
        longitude = this.coordinates.longitude,
        latitude = this.coordinates.latitude,
        name = this.name,
        typeName = this.typeName,
        city = LocationCityEntity(
            name = this.city.name,
            timeZone = this.city.timeZone
        ),
        country = LocationCountryEntity(
            name = this.country.name
        )
    )
}

fun LocationEntity.toDomain() : Location {
    return Location(
        id = this.id,
        coordinates = Coordinates(
            latitude = this.latitude,
            longitude = this.longitude
        ),
        name = this.name,
        typeName = this.typeName,
        city = LocationCity(
            name = this.city.name,
            timeZone = this.city.timeZone
        ),
        country = LocationCountry(
            name = this.country.name
        )
    )
}

fun LocationStructureDto.toDomain() : LocationStructure {
    return LocationStructure(
        solarPanels = this.solarPanels.map {
            LocationDevice(
                id = it.id,
                name = it.name,
                count = it.count
            )
        },
        windMills = this.windMills.map {
            LocationDevice(
                id = it.id,
                name = it.name,
                count = it.count
            )
        },
        accumulators = this.accumulators.map {
            LocationDevice(
                id = it.id,
                name = it.name,
                count = it.count
            )
        }
    )
}