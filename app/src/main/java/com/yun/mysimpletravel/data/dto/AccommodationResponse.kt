package com.yun.mysimpletravel.data.dto

import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationModel

data class AccommodationResponse(
    val totCnt: Int,
    val hasMore: Boolean,
    val data: List<AccommodationData>?
)

data class AccommodationData(
    val companyName: String,        // 사업장명
    val bizLargeType: String,       // 업종 구분 대분류
    val bizSmallType: String,       // 업종 구분 소분류
    val licensingDate: String,      // 인허가 일자
    val openStatus: String,         // 영업 상태명
    val addressJibun: String,       // 지번 주소
    val addressDoro: String,        // 도로명 주소
    val roomCount: String,          // 객실 수
    val updateAt: String            // 데이터 갱신 일시
)