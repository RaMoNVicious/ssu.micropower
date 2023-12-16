package com.ssu.micropower.models.dao

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "location_id")
    val locationId: Long,
    val latitude: Float,
    val longitude: Float,
    @Embedded(prefix = "current_")
    val current: WeatherCurrentEntity,
    //@Embedded(prefix = "hourly")
    //val hourly: HourlyDaoEntity
)
