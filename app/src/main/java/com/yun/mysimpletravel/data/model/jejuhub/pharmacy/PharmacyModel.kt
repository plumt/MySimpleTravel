package com.yun.mysimpletravel.data.model.jejuhub.pharmacy

import com.yun.mysimpletravel.data.dto.PharmacyData

data class PharmacyModel(
    val list: List<PharmacyList>?,
    val totCnt: Int,
    val hasMore: Boolean
)

data class PharmacyList(
    val companyName: String,
    var longitude: String,
    var latitude: String,
    val addressDoro: String,
    val addressJibun: String
)
