package com.yun.mysimpletravel.data.repository.jejuhub

import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationModel
import com.yun.mysimpletravel.data.model.travel.carsharing.CarsharingModel
import com.yun.mysimpletravel.data.remote.GetDataCallBack
import com.yun.mysimpletravel.data.remote.api.jejuhub.jejuHubServiceImpl.toAccommodationModelList
import com.yun.mysimpletravel.data.remote.api.jejuhub.jejuHubServiceImpl.toCarsharingModelList
import javax.inject.Inject

class JejuHubRepositoryImpl @Inject constructor(
    private val jejuHubRepository: JejuHubRepository,
) {

//    interface GetDataCallBack<T> {
//        fun onSuccess(data: T)
//        fun onFailure(throwable: Throwable)
//    }

    suspend operator fun invoke(
        page: String, companyName: String,
        callBack: GetDataCallBack<AccommodationModel>,
    ) {

        jejuHubRepository.searchAccommodation(
            page, companyName,
            { callBack.invoke(it.toAccommodationModelList(), null) },
            { callBack.invoke(null, it) }
        )
    }

    suspend fun carsharingWithSocar(
        page: String = "1",
        callBack: GetDataCallBack<CarsharingModel>,
    ) {
        jejuHubRepository.searchCarsharingWithSocar(
            page,
            { callBack.invoke(it.toCarsharingModelList(), null) },
            { callBack.invoke(null, it) }
        )
    }

    suspend fun carsharing(
        page: String = "1",
        callBack: GetDataCallBack<CarsharingModel>,
    ) {
//        jejuHubRepository.searchsearchCarsharing(
//            page,
//            { callBack.onSuccess(it.toCarsharingModelList()) },
//            { callBack.onFailure(it) }
//        )
        jejuHubRepository.searchsearchCarsharing(
            page,
            { callBack.invoke(it.toCarsharingModelList(), null) },
            { callBack.invoke(null, it) }
        )
    }
}