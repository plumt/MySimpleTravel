package com.yun.mysimpletravel.data.model.travel.souvenir


data class SouvenirModel(
    var list: List<SouvenirList>?,
    val totCnt: Int,
    val hasMore: Boolean,
)

data class SouvenirList(
    val placeName: String,
    val longitude: String,
    val latitude: String,
    val placeUrl: String,
    val addressDoro: String,
    val addressJibun: String
)
