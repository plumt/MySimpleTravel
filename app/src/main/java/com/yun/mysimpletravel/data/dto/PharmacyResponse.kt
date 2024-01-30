package com.yun.mysimpletravel.data.dto

data class PharmacyResponse(
    val totCnt: Int,
    val hasMore: Boolean,
    val data: List<PharmacyData>?,
)

data class PharmacyData(
    val companyName: String,
    val bizLargeType: String,
    val licensingDate: String,
    val licensingCancelDate: String,
    val openStatus: String,
    val closeDate: String,
    val holidayStartDate: String,
    val holidayEndDate: String,
    val reopenDate: String,
    val addressJibun: String,
    val addressDoro: String,
    val postcodeDoro: String,
    val updateAt: String,
)