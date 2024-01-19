package com.yun.mysimpletravel.data.model.travel.accommodation

data class AccommodationModel(
    val list: List<AccommodationList>,
    val totCnt: Int,
    val hasMore: Boolean,
)

data class AccommodationList(
    val companyName: String,        // 사업장명
    val bizLargeType: String,       // 업종 구분 대분류
    val bizSmallType: String,       // 업종 구분 소분류
    val openStatus: String,         // 영업 상태명
    val addressJibun: String,       // 지번 주소
    val addressDoro: String,        // 도로명 주소
    val roomCount: String?,          // 객실 수
)