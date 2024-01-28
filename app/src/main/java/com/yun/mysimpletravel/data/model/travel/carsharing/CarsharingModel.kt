package com.yun.mysimpletravel.data.model.travel.carsharing

data class CarsharingModel(
    val list: List<CarsharingList>?,
    val totCnt: Int,
    val hasMore: Boolean,
)

data class CarsharingList(
    val placeName: String,
    val longitude: String,
    val latitude: String,
    val placeUrl: String,
    val addressDoro: String,
    val addressJibun: String
)