package com.ssu.micropower.models.api

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("loc_id")
    val locationId: Long,
    @SerializedName("loc_latitude")
    val latitude: Float,
    @SerializedName("loc_longitude")
    val longitude: Float,
    @SerializedName("loc_name")
    val name: String,
    @SerializedName("lt_type_title")
    val typeName: String,
    @SerializedName("city_name")
    val cityName: String,
    @SerializedName("city_time_zone")
    val cityTimeZone: Int,
    @SerializedName("country_name")
    val countryName: String
)