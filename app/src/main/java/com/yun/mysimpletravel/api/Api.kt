package com.yun.mysimpletravel.api

import com.yun.mysimpletravel.data.model.location.LocationDataModel
import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
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


    /**
     * 관광 숙박업 정보
     */
//    @Headers("Cache-Control: public, max-stale=10")
    @GET("/api/proxy/ttbttaD3aaa3ta8bbtDtaDD18t37aD17/3err3be_jt2oeto22rtt_932291tp__2")
    suspend fun searchAccommodation(
        @Query("page") page: String,
        @Query("limit") limit: String,
        @Query("companyName") companyName: String
    ): Response<AccommodationDataModel.RS>
}