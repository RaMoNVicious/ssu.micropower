package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class GenerationBatteryDto(
    @SerializedName("current_capacity_data")
    val capacity: CapacityDto,
    val status: List<BatteryStatusDto>,
    @SerializedName("max_capacity")
    val maxCapacity: Int
)

data class CapacityDto(
    val time: String,
    @SerializedName("current_capacity")
    val value: Float,
)

data class BatteryStatusDto(
    @SerializedName("accum_id")
    val batteryId: Int,
    @SerializedName("device_id")
    val deviceId: Int,
    val charge: Byte,
    val discharge: Byte,
    val convector: Byte,
)