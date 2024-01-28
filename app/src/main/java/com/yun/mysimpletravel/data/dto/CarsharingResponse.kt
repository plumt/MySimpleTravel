package com.yun.mysimpletravel.data.dto

data class CarsharingResponse(
    val totCnt: Int,
    val hasMore: Boolean,
    val data: List<Carsharing>?
)

data class Carsharing(
    val placeName: String,
    val category: String,
    val addressJibun: String,
    val addressDoro: String,
    val longitude: String,
    val latitude: String,
    val placeUrl: String
)