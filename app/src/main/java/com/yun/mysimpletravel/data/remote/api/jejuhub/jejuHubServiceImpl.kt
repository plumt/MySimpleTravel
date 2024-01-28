package com.yun.mysimpletravel.data.remote.api.jejuhub

import com.yun.mysimpletravel.data.dto.AccommodationResponse
import com.yun.mysimpletravel.data.dto.CarsharingResponse
import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationList
import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationModel
import com.yun.mysimpletravel.data.model.travel.carsharing.CarsharingList
import com.yun.mysimpletravel.data.model.travel.carsharing.CarsharingModel

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
        return CarsharingModel(list = this.data?.map {
            CarsharingList(
                placeName = it.placeName,
                longitude = it.longitude,
                latitude = it.latitude,
                placeUrl = it.placeUrl,
                addressDoro = it.addressDoro,
                addressJibun = it.addressJibun
            )
        } ?: emptyList(), this.totCnt, this.hasMore)
    }
}