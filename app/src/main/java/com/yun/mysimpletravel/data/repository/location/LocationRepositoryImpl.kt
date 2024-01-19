package com.yun.mysimpletravel.data.repository.location

import com.yun.mysimpletravel.data.model.location.LocationModel
import com.yun.mysimpletravel.data.remote.api.location.LocationServiceImpl.toLocationModelList
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRepository: LocationRepository
) {

    interface GetDataCallBack<T> {
        fun onSuccess(data: T)
        fun onFailure(throwable: Throwable)
    }

    suspend operator fun invoke(
        code: String,
        callBack: GetDataCallBack<List<LocationModel>>
    ) {

        locationRepository.getLocationCode(
            code,
            { callBack.onSuccess(it.toLocationModelList() ?: listOf()) },
            { callBack.onFailure(it) }
        )
    }
}