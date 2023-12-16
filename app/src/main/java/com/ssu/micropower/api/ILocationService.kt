package com.ssu.micropower.api

import com.ssu.micropower.models.api.BaseResponse
import com.ssu.micropower.models.api.LocationDto
import com.ssu.micropower.models.api.LocationStructureDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ILocationService {

    @GET("customers/{customerId}/locations")
    suspend fun getLocations(
        @Path("customerId") customerId: Long
    ): Response<BaseResponse<List<LocationDto>>>

    @GET("customers/{customerId}/microgrids/{locationId}/structure")
    suspend fun getLocationStructure(
        @Path("customerId") customerId: Long,
        @Path("locationId") locationId: Long
    ) : Response<BaseResponse<LocationStructureDto>>

    @GET("customers/{customerId}/microgrids/{locationId}/components")
    suspend fun getLocationComponents(
        @Path("customerId") customerId: Long,
        @Path("locationId") locationId: Long
    ) : Response<BaseResponse<List<Any>>>
}