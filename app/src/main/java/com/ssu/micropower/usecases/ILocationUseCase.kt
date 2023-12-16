package com.ssu.micropower.usecases

import com.ssu.micropower.api.Result
import com.ssu.micropower.models.domain.Location
import com.ssu.micropower.models.domain.LocationStructure
import kotlinx.coroutines.flow.Flow

interface ILocationUseCase {
    suspend fun getLocations(): Flow<Result<List<Location>>>

    suspend fun getLocationsLocal(): Flow<Result<List<Location>>>

    suspend fun getStructure(locationId: Long): Flow<Result<LocationStructure>>

    suspend fun getComponents(locationId: Long): Flow<Result<List<Any>>>
}