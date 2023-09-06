package com.yun.mysimpletravel.api

import com.yun.mysimpletravel.data.model.location.LocationDataModel
import com.yun.mysimpletravel.data.model.weather.NowWeatherDataModel
import com.yun.mysimpletravel.util.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    /**
     * 행정구역 코드 조회
     */
    @GET("/v1/regcodes")
    suspend fun searchLocationCode(
        @Query("regcode_pattern") regcode_pattern: String,
        @Query("is_ignore_zero") is_ignore_zero: Boolean = true
    ): Response<LocationDataModel.RS>

}