package com.yun.mysimpletravel.data.dto

data class SouvenirResponse(
    val totCnt: Int,
    val hasMore: Boolean,
    val data: List<SouvenirData>?
)

data class SouvenirData(
    val placeName: String,
    val category: String,
    val addressJibun: String,
    val addressDoro: String,
    val longitude: String,
    val latitude: String,
    val placeUrl: String
)