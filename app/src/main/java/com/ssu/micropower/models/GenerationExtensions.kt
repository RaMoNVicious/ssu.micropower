package com.ssu.micropower.models

import com.ssu.micropower.models.api.GenerationBatteryDto
import com.ssu.micropower.models.api.GenerationSolarDto
import com.ssu.micropower.models.api.GenerationWindDto
import com.ssu.micropower.models.domain.GenerationItem
import com.ssu.micropower.models.domain.GenerationItemDevice
import com.ssu.micropower.models.domain.GenerationItemType
import com.ssu.micropower.models.domain.GenerationItemValue
import com.ssu.micropower.models.domain.LocationDevice

fun GenerationSolarDto.toGenerationItem(devices: List<GenerationItemDevice> = listOf()): GenerationItem {
    return GenerationItem(
        type = GenerationItemType.Solar,
        maxPower = this.maxPower.toFloat() / 1000f,
        valueCurrent = GenerationItemValue(
            time = this.dataCurrent.time,
            value = this.dataCurrent.generation,
            conditions = this.dataCurrent.clouds
        ),
        valueForecast = GenerationItemValue(
            time = this.dataForecast.time,
            value = this.dataForecast.generation,
            conditions = this.dataForecast.clouds
        ),
        devices = devices
    )
}

fun GenerationWindDto.toGenerationItem(devices: List<GenerationItemDevice> = listOf()): GenerationItem {
    return GenerationItem(
        type = GenerationItemType.Wind,
        maxPower = this.maxPower.toFloat() / 1000f,
        valueCurrent = GenerationItemValue(
            time = this.dataCurrent.time,
            value = this.dataCurrent.generation,
            conditions = this.dataCurrent.windSpeed
        ),
        valueForecast = GenerationItemValue(
            time = this.dataForecast.time,
            value = this.dataForecast.generation,
            conditions = this.dataForecast.windSpeed
        ),
        devices = devices
    )
}

fun GenerationBatteryDto.toGenerationItem(devices: List<GenerationItemDevice> = listOf()): GenerationItem {
    return GenerationItem(
        type = GenerationItemType.Battery,
        maxPower = this.maxCapacity.toFloat() / 1000f,
        valueCurrent = GenerationItemValue(
            time = this.capacity.time,
            value = this.capacity.value / 1000f,
            conditions = 0f
        ),
        valueForecast = GenerationItemValue(
            time = "",
            value = 0f,
            conditions = 0f
        ),
        devices = devices
    )
}

fun LocationDevice.toGenerationItemDevice(): GenerationItemDevice {
    return GenerationItemDevice(
        name = this.name,
        count = this.count
    )
}