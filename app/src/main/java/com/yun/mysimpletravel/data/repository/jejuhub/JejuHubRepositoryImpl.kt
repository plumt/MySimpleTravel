package com.yun.mysimpletravel.data.repository.jejuhub

import android.util.Log
import com.yun.mysimpletravel.data.model.location.LocationModel
import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationModel
import com.yun.mysimpletravel.data.remote.api.jejuhub.jejuHubServiceImpl.toAccommodationModelList
import com.yun.mysimpletravel.data.repository.location.LocationRepository
import javax.inject.Inject

class JejuHubRepositoryImpl @Inject constructor(
    private val jejuHubRepository: JejuHubRepository
) {

    interface GetDataCallBack<T> {
        fun onSuccess(data: T)
        fun onFailure(throwable: Throwable)
    }

    suspend operator fun invoke(
        page: String, companyName: String,
        callBack: GetDataCallBack<AccommodationModel>
    ) {

        jejuHubRepository.searchAccommodation(
            page, companyName,
            { callBack.onSuccess(it.toAccommodationModelList()) },
            { callBack.onFailure(it) }
        )
    }
}