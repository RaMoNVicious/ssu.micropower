package com.ssu.micropower.models.dao

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey
    val id: Long,
    val latitude: Float,
    val longitude: Float,
    val name: String,
    @ColumnInfo(name = "type_name")
    val typeName: String,
    @Embedded(prefix = "city_")
    val city: LocationCityEntity,
    @Embedded(prefix = "country_")
    val country: LocationCountryEntity
)
