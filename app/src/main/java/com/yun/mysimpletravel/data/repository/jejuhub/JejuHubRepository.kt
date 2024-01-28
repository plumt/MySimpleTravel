package com.yun.mysimpletravel.data.repository.jejuhub

import com.yun.mysimpletravel.data.dto.AccommodationResponse
import com.yun.mysimpletravel.data.dto.CarsharingResponse
import com.yun.mysimpletravel.data.remote.RemoteDataSourceImpl
import com.yun.mysimpletravel.data.remote.api.jejuhub.JejuHubService
import javax.inject.Inject

class JejuHubRepository @Inject constructor(
    private val jejuHubService: JejuHubService
){
    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    suspend fun searchAccommodation(
        page: String, companyName: String,
        onResponse: (AccommodationResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        remoteDataSourceImpl.callApi(jejuHubService.searchAccommodation(page,"100",companyName), onResponse, onFailure)
    }

    suspend fun searchCarsharingWithSocar(
        onResponse: (CarsharingResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        remoteDataSourceImpl.callApi(jejuHubService.searchCarsharingWithSocar(), onResponse, onFailure)
    }

    suspend fun searchsearchCarsharing(
        onResponse: (CarsharingResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        remoteDataSourceImpl.callApi(jejuHubService.searchCarsharing(),onResponse, onFailure)
    }
}