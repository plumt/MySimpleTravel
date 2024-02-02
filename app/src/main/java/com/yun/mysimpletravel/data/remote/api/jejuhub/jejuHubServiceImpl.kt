package com.yun.mysimpletravel.data.remote.api.jejuhub

import android.content.Context
import com.yun.mysimpletravel.data.dto.AccommodationResponse
import com.yun.mysimpletravel.data.dto.CarsharingResponse
import com.yun.mysimpletravel.data.dto.PharmacyResponse
import com.yun.mysimpletravel.data.dto.SouvenirResponse
import com.yun.mysimpletravel.data.model.jejuhub.accommodation.AccommodationList
import com.yun.mysimpletravel.data.model.jejuhub.accommodation.AccommodationModel
import com.yun.mysimpletravel.data.model.jejuhub.carsharing.CarsharingList
import com.yun.mysimpletravel.data.model.jejuhub.carsharing.CarsharingModel
import com.yun.mysimpletravel.data.model.jejuhub.pharmacy.PharmacyList
import com.yun.mysimpletravel.data.model.jejuhub.pharmacy.PharmacyModel
import com.yun.mysimpletravel.data.model.jejuhub.souvenir.SouvenirList
import com.yun.mysimpletravel.data.model.jejuhub.souvenir.SouvenirModel
import com.yun.mysimpletravel.util.LocationUtil.getAddressFromLocationName

object jejuHubServiceImpl {
    fun AccommodationResponse.toAccommodationModelList(): AccommodationModel {
        return AccommodationModel(list = this.data?.map {
            AccommodationList(
                companyName = it.companyName,
                bizLargeType = it.bizLargeType,
                bizSmallType = it.bizSmallType,
                openStatus = it.openStatus,
                addressJibun = it.addressJibun,
                addressDoro = it.addressDoro,
                roomCount = it.roomCount
            )
        } ?: emptyList(), this.totCnt, this.hasMore)
    }

    fun CarsharingResponse.toCarsharingModelList(): CarsharingModel {
        return CarsharingModel(list = this.data?.mapIndexed { index, it ->
            CarsharingList(
                id = index,
                placeName = it.placeName,
                longitude = it.longitude,
                latitude = it.latitude,
                placeUrl = it.placeUrl,
                addressDoro = it.addressDoro,
                addressJibun = it.addressJibun
            )
        } ?: emptyList(), this.totCnt, this.hasMore)
    }

    fun SouvenirResponse.toSouvenirModelList(): SouvenirModel {
        return SouvenirModel(list = this.data?.map {
            SouvenirList(
                placeName = it.placeName,
                longitude = it.longitude,
                latitude = it.latitude,
                placeUrl = it.placeUrl,
                addressDoro = it.addressDoro,
                addressJibun = it.addressJibun
            )
        } ?: emptyList(), this.totCnt, this.hasMore)
    }

    fun PharmacyResponse.toPharmacyModelList(context: Context): PharmacyModel {
        return PharmacyModel(list = this.data?.map {
            val latLng = getAddressFromLocationName(context,"제주특별자치도 제주시 번영로 500, 1층 (봉개동)")?.let {
//                Log.d("lys","getAddressFromLocationName > ${it.latitude}  ${it.longitude}")
                Pair(it.latitude.toString(), it.longitude.toString())
            }
            PharmacyList(
                companyName = it.companyName,
                longitude = latLng?.second ?: "",
                latitude = latLng?.first ?: "",
                addressDoro = it.addressDoro,
                addressJibun = it.addressJibun
            )
        } ?: emptyList(), this.totCnt, this.hasMore)
    }
}