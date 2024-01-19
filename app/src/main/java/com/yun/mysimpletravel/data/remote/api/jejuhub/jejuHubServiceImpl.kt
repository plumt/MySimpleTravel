package com.yun.mysimpletravel.data.remote.api.jejuhub

import com.yun.mysimpletravel.data.dto.AccommodationResponse
import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationList
import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationModel

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
}