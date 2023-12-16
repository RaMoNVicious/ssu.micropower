package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class LocationStructureDto(
    @SerializedName("solar")
    val solarPanels: List<LocationDeviceDto>,
    @SerializedName("wind")
    val windMills: List<LocationDeviceDto>,
    @SerializedName("accum")
    val accumulators: List<LocationDeviceDto>,
)
