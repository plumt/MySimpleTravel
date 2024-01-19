package com.yun.mysimpletravel.data.repository.location

import com.yun.mysimpletravel.data.dto.LocationCodeResponse
import com.yun.mysimpletravel.data.remote.RemoteDataSourceImpl
import com.yun.mysimpletravel.data.remote.api.location.LocationService
import retrofit2.Response
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationService: LocationService
){
    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    suspend fun getLocationCode(
        code: String,
        onResponse: (LocationCodeResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        remoteDataSourceImpl.callApi(locationService.searchLocationCode(code), onResponse, onFailure)
    }
}