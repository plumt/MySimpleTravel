package com.yun.mysimpletravel.api

import com.yun.mysimpletravel.data.model.weather.NowWeatherDataModel
import com.yun.mysimpletravel.util.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    /**
     * 현재 날씨
     */
    @GET("/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst")
    suspend fun nowWeather(
        @Query("nx") nx: String,
        @Query("ny") ny: String,
        @Query("base_date") base_date: String,
        @Query("base_time") base_time: String,
        @Query("dataType") dataType: String = "JSON",
        @Query("numOfRows") numOfRows: String = "1000",
        @Query("pageNo") pageNo: String = "1",
        @Query("serviceKey") serviceKey: String = Util.serviceKey()
    ): Response<NowWeatherDataModel.RS>


}