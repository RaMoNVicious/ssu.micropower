package com.ssu.micropower.models.domain

import java.io.Serializable

data class Location(
    val id: Long,
    val coordinates: Coordinates,
    val name: String,
    val typeName: String,
    val city: LocationCity,
    val country: LocationCountry,
) : Serializable
