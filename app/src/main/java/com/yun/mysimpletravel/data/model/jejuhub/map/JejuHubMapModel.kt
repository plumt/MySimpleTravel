package com.yun.mysimpletravel.data.model.jejuhub.map

import com.yun.mysimpletravel.base.Item

//data class JejuHubMapModel(
//    var list: List<JejuHubMapList>?,
//    val totCnt: Int,
//    val hasMore: Boolean,
//)
//
//data class JejuHubMapList(
//    override var id: Int
//): Item()


data class JejuHubMapModel(
    var list: List<JejuHubMapList>?,
    val totCnt: Int,
    val hasMore: Boolean,
)

data class JejuHubMapList(
    override var id: Int,
    val placeName: String,
    val longitude: String,
    val latitude: String,

    // 렌트카
    val placeUrl: String?,
    val addressDoro: String?,
    val addressJibun: String?
): Item() {

    fun address(): String = if(addressDoro.isNullOrEmpty()) addressJibun?:"" else addressDoro

}