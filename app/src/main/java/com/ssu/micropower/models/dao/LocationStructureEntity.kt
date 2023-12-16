package com.ssu.micropower.models.dao

import androidx.room.Entity
import com.ssu.micropower.models.api.LocationDeviceDto

//@Entity(tableName = "location_structure")
data class LocationStructureEntity(
    val id: Long,
    val locationId: Long,
    val solarPanels: List<LocationDeviceDto>,
    val windMills: List<LocationDeviceDto>,
    val accumulators: List<LocationDeviceDto>,
)