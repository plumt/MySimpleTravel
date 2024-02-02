package com.yun.mysimpletravel.data.model.jejuhub.carsharing

import com.yun.mysimpletravel.base.Item

data class CarsharingModel(
    var list: List<CarsharingList>?,
    val totCnt: Int,
    val hasMore: Boolean,
)

data class CarsharingList(
    override var id: Int,
    val placeName: String,
    val longitude: String,
    val latitude: String,
    val placeUrl: String,
    val addressDoro: String,
    val addressJibun: String
): Item()