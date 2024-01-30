package com.yun.mysimpletravel.data.repository.jejuhub

import android.content.Context
import com.yun.mysimpletravel.data.model.jejuhub.accommodation.AccommodationModel
import com.yun.mysimpletravel.data.model.jejuhub.carsharing.CarsharingModel
import com.yun.mysimpletravel.data.model.jejuhub.pharmacy.PharmacyModel
import com.yun.mysimpletravel.data.model.jejuhub.souvenir.SouvenirModel
import com.yun.mysimpletravel.data.remote.GetDataCallBack
import com.yun.mysimpletravel.data.remote.api.jejuhub.jejuHubServiceImpl.toAccommodationModelList
import com.yun.mysimpletravel.data.remote.api.jejuhub.jejuHubServiceImpl.toCarsharingModelList
import com.yun.mysimpletravel.data.remote.api.jejuhub.jejuHubServiceImpl.toPharmacyModelList
import com.yun.mysimpletravel.data.remote.api.jejuhub.jejuHubServiceImpl.toSouvenirModelList
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JejuHubRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
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

    suspend fun souvenir(
        page: String = "1",
        callBack: GetDataCallBack<SouvenirModel>,
    ) {
        jejuHubRepository.searchSouvenir(page,
            { callBack.invoke(it.toSouvenirModelList(), null) },
            { callBack.invoke(null, it) })
    }

    suspend fun pharmacy(
        page: String = "1",
        callBack: GetDataCallBack<PharmacyModel>,
    ) {
        jejuHubRepository.searchPharmacy(page,
            { callBack.invoke(it.toPharmacyModelList(context), null) },
            { callBack.invoke(null, it) })
    }
}