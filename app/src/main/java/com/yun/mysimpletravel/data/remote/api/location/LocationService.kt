package com.yun.mysimpletravel.data.remote.api.location

import com.yun.mysimpletravel.data.dto.LocationCodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {

    /**
     * 행정구역 코드 조회
     */
    @GET("/v1/regcodes")
    suspend fun searchLocationCode(
        @Query("regcode_pattern") regcode_pattern: String,
        @Query("is_ignore_zero") is_ignore_zero: Boolean = true
    ): Response<LocationCodeResponse>


}