package com.ssu.micropower.models.domain

data class LocationStructure(
    val solarPanels: List<LocationDevice>,
    val windMills: List<LocationDevice>,
    val accumulators: List<LocationDevice>,
)
