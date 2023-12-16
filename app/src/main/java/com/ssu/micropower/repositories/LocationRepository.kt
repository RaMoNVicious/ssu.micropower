package com.ssu.micropower.repositories

import com.ssu.micropower.api.INetworkManager
import com.ssu.micropower.api.ILocationService
import com.ssu.micropower.api.Result
import com.ssu.micropower.api.consumeRequest
import com.ssu.micropower.models.api.LocationDto
import com.ssu.micropower.models.api.LocationStructureDto
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationService: ILocationService,
    private val networkManager: INetworkManager,
) : ILocationRepository {
    override suspend fun getLocations(customerId: Long): Result<List<LocationDto>> {
        return networkManager.consumeRequest { locationService.getLocations(customerId) }
    }

    override suspend fun getStructure(
        customerId: Long,
        locationId: Long
    ): Result<LocationStructureDto> {
        return networkManager.consumeRequest { locationService.getLocationStructure(customerId, locationId) }
    }

    override suspend fun getComponents(
        customerId: Long,
        locationId: Long
    ): Result<List<Any>> {
        return networkManager.consumeRequest { locationService.getLocationComponents(customerId, locationId) }
    }
}