package com.ssu.micropower.usecases

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.ssu.micropower.api.Result
import com.ssu.micropower.api.onFailure
import com.ssu.micropower.api.onSuccess
import com.ssu.micropower.models.api.GenerationBatteryDto
import com.ssu.micropower.models.api.GenerationSolarDto
import com.ssu.micropower.models.api.GenerationStateDto
import com.ssu.micropower.models.api.GenerationWindDto
import com.ssu.micropower.models.dao.ILocationDao
import com.ssu.micropower.models.dao.IUserDao
import com.ssu.micropower.models.domain.Location
import com.ssu.micropower.models.domain.LocationStructure
import com.ssu.micropower.models.toDao
import com.ssu.micropower.models.toDomain
import com.ssu.micropower.repositories.ILocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationUseCase @Inject constructor(
    private val locationRepository: ILocationRepository,
    private val locationDao: ILocationDao,
    private val userDao: IUserDao,
) : ILocationUseCase {
    override suspend fun getLocations(): Flow<Result<List<Location>>> {
        return flow {
            val customer = userDao.getUser()

            locationRepository
                .getLocations(customer.id)
                .onSuccess { locations ->
                    locations
                        .map { it.toDomain() }
                        .also { emit(Result.Success(it)) }
                        .map { it.toDao() }
                        .also { locationDao.insert(it) }
                }
                .onFailure {
                    emit(Result.Failure(it))
                }
        }
    }

    override suspend fun getLocationsLocal(): Flow<Result<List<Location>>> {
        return flow {
            emit(
                Result.Success(
                    locationDao
                        .getLocations()
                        .map { it.toDomain() }
                )
            )
        }
    }

    override suspend fun getStructure(locationId: Long): Flow<Result<LocationStructure>> {
        val customer = userDao.getUser()
        return flow {
            locationRepository
                .getStructure(customer.id, locationId)
                .onSuccess { structure ->
                    structure
                        .toDomain()
                        .also {
                            emit(Result.Success(it))
                            // TODO: save to DB
                        }
                }
                .onFailure {
                    emit(Result.Failure(it))
                }
        }
    }

    override suspend fun getComponents(locationId: Long): Flow<Result<List<Any>>> {
        val customer = userDao.getUser()
        return flow {
            locationRepository
                .getComponents(customer.id, locationId)
                .onSuccess { components ->
                    println(components)
                    // TODO:
                    val result = components
                        .filterIsInstance<LinkedTreeMap<*, *>>()
                        .map { it as LinkedTreeMap<*, *> }
                        .map {
                            Gson().fromJson(
                                Gson().toJsonTree(it),
                                when {
                                    it.containsKey("actual_solar_generation_data") -> GenerationSolarDto::class.java
                                    it.containsKey("actual_wind_generation_data") -> GenerationWindDto::class.java
                                    it.containsKey("current_capacity_data") -> GenerationBatteryDto::class.java
                                    it.containsKey("current_system_state_data") -> GenerationStateDto::class.java
                                    else -> Any::class.java
                                }
                            )
                        }
                    emit(Result.Success(result))
                }
                .onFailure {
                    emit(Result.Failure(it))
                }
        }
    }
}