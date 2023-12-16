package com.ssu.micropower.models.domain

import java.util.Date

data class LocationInfo(
    val location: Location,
    val time: Date,
    val consumption: Float,
    val sell: Float,
    val purchase: Float,
    val generation: List<GenerationItem>
)

data class GenerationItem(
    val type: GenerationItemType,
    val maxPower: Float,
    val valueCurrent: GenerationItemValue,
    val valueForecast: GenerationItemValue,
    val devices: List<GenerationItemDevice>
)

enum class GenerationItemType {
    Solar, Wind, Battery
}

data class GenerationItemValue(
    val time: String,
    val value: Float,
    val conditions: Float
)

data class GenerationItemDevice(
    val name: String,
    val count: Int,
)