package com.yun.mysimpletravel.data.repository.jejuhub

import android.content.Context
import com.yun.mysimpletravel.data.dto.AccommodationResponse
import com.yun.mysimpletravel.data.dto.CarsharingResponse
import com.yun.mysimpletravel.data.dto.PharmacyResponse
import com.yun.mysimpletravel.data.dto.SouvenirResponse
import com.yun.mysimpletravel.data.remote.RemoteDataSourceImpl
import com.yun.mysimpletravel.data.remote.api.jejuhub.JejuHubService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JejuHubRepository @Inject constructor(
    private val jejuHubService: JejuHubService
) {
    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    suspend fun searchAccommodation(
        page: String, companyName: String,
        onResponse: (AccommodationResponse) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        remoteDataSourceImpl.callApi(
            jejuHubService.searchAccommodation(page, "100", companyName),
            onResponse,
            onFailure
        )
    }

    suspend fun searchCarsharingWithSocar(
        page: String,
        onResponse: (CarsharingResponse) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        remoteDataSourceImpl.callApi(
            jejuHubService.searchCarsharingWithSocar(page, "100"),
            onResponse,
            onFailure
        )
    }

    suspend fun searchsearchCarsharing(
        page: String,
        onResponse: (CarsharingResponse) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        remoteDataSourceImpl.callApi(
            jejuHubService.searchCarsharing(page, "100"),
            onResponse,
            onFailure
        )
    }

    suspend fun searchSouvenir(
        page: String,
        onResponse: (SouvenirResponse) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        remoteDataSourceImpl.callApi(
            jejuHubService.searchSouvenir(page, "100"),
            onResponse,
            onFailure
        )
    }

    suspend fun searchPharmacy(
        page: String,
        onResponse: (PharmacyResponse) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        remoteDataSourceImpl.callApi(
            jejuHubService.searchPharmacy("영업", page, "100"),
            onResponse,
            onFailure
        )
    }
}