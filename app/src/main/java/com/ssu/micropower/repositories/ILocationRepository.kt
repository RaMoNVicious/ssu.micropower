package com.ssu.micropower.repositories

import com.ssu.micropower.api.Result
import com.ssu.micropower.models.api.LocationDto
import com.ssu.micropower.models.api.LocationStructureDto

interface ILocationRepository {

    suspend fun getLocations(customerId: Long): Result<List<LocationDto>>

    suspend fun getStructure(customerId: Long, locationId: Long): Result<LocationStructureDto>

    suspend fun getComponents(customerId: Long, locationId: Long): Result<List<Any>>
}