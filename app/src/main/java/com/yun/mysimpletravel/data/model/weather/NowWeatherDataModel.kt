package com.yun.mysimpletravel.data.model.weather

class NowWeatherDataModel {
    data class RS(
        val response: Response
    )

    data class Response(
        val header: Header,
        val body: Body?
    )

    data class Header(
        val resultCode: String,
        val resultMsg: String
    )

    data class Body(
        val dataType: String,
        val items: Items,
        val pageNo: String,
        val numOfRows: String,
        val totalCount: String
    )

    data class Items(
        val item: List<Item>?
    )

    data class Item(
        val baseDate: String,
        val baseTime: String,
        val category: String,
        val nx: String,
        val ny: String,
        val obsrValue: String?,     // 실황 값
        val fcstValue: String?      // 예보 값
    )
}