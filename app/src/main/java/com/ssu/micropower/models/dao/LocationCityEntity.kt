package com.ssu.micropower.models.dao

import androidx.room.ColumnInfo

data class LocationCityEntity(
    val name: String,
    @ColumnInfo(name = "time_zone")
    val timeZone: Int,
)