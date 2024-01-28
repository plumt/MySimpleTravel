package com.yun.mysimpletravel.data.repository.jejuhub

import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationModel
import com.yun.mysimpletravel.data.model.travel.carsharing.CarsharingModel
import com.yun.mysimpletravel.data.remote.api.jejuhub.jejuHubServiceImpl.toAccommodationModelList
import com.yun.mysimpletravel.data.remote.api.jejuhub.jejuHubServiceImpl.toCarsharingModelList
import javax.inject.Inject

class JejuHubRepositoryImpl @Inject constructor(
    private val jejuHubRepository: JejuHubRepository,
) {

    interface GetDataCallBack<T> {
        fun onSuccess(data: T)
        fun onFailure(throwable: Throwable)
    }

    suspend operator fun invoke(
        page: String, companyName: String,
        callBack: GetDataCallBack<AccommodationModel>,
    ) {

        jejuHubRepository.searchAccommodation(
            page, companyName,
            { callBack.onSuccess(it.toAccommodationModelList()) },
            { callBack.onFailure(it) }
        )
    }

    suspend fun carsharingWithSocar(
        callBack: GetDataCallBack<CarsharingModel>,
    ) {
        jejuHubRepository.searchCarsharingWithSocar(
            { callBack.onSuccess(it.toCarsharingModelList()) },
            { callBack.onFailure(it) }
        )
    }

    suspend fun carsharing(
        callBack: GetDataCallBack<CarsharingModel>,
    ) {
        jejuHubRepository.searchsearchCarsharing(
            { callBack.onSuccess(it.toCarsharingModelList()) },
            { callBack.onFailure(it) }
        )
    }
}