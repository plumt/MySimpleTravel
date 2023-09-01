package com.yun.mysimpletravel.api

import com.yun.mysimpletravel.data.model.location.LocationDataModel
import com.yun.mysimpletravel.data.model.weather.NowWeatherDataModel
import com.yun.mysimpletravel.util.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    /**
     * 초단기 실황
     */
    @GET("getUltraSrtNcst")
    suspend fun srtNcst(
        @Query("nx") nx: String,
        @Query("ny") ny: String,
        @Query("base_date") base_date: String,
        @Query("base_time") base_time: String,
        @Query("dataType") dataType: String = "JSON",
        @Query("numOfRows") numOfRows: String = "1000",
        @Query("pageNo") pageNo: String = "1",
        @Query("serviceKey") serviceKey: String = Util.serviceKey()
    ): Response<NowWeatherDataModel.RS>

    /**
     * 단기 예보
     */
    @GET("getVilageFcst")
    suspend fun fcst(
        @Query("nx") nx: String,
        @Query("ny") ny: String,
        @Query("base_date") base_date: String,
        @Query("base_time") base_time: String,
        @Query("dataType") dataType: String = "JSON",
        @Query("numOfRows") numOfRows: String = "1000",
        @Query("pageNo") pageNo: String = "1",
        @Query("serviceKey") serviceKey: String = Util.serviceKey()
    ): Response<NowWeatherDataModel.RS>

    /**
     * 초단기 예보
     */
    @GET("getUltraSrtFcst")
    suspend fun srtFcst(
        @Query("nx") nx: String,
        @Query("ny") ny: String,
        @Query("base_date") base_date: String,
        @Query("base_time") base_time: String,
        @Query("dataType") dataType: String = "JSON",
        @Query("numOfRows") numOfRows: String = "1000",
        @Query("pageNo") pageNo: String = "1",
        @Query("serviceKey") serviceKey: String = Util.serviceKey()
    ): Response<NowWeatherDataModel.RS>

    /**
     * 행정구역 코드 조회
     */
    @GET("/v1/regcodes")
    suspend fun searchLocationCode(
        @Query("regcode_pattern") regcode_pattern: String,
        @Query("is_ignore_zero") is_ignore_zero: Boolean = true
    ): Response<LocationDataModel.RS>

}