package com.yun.mysimpletravel.data.remote.api.jejuhub

import com.yun.mysimpletravel.data.dto.AccommodationResponse
import com.yun.mysimpletravel.data.dto.CarsharingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JejuHubService {

    /**
     * 관광 숙박업 정보
     */
//    @Headers("Cache-Control: public, max-stale=10")
    @GET("ttbttaD3aaa3ta8bbtDtaDD18t37aD17/3err3be_jt2oeto22rtt_932291tp__2")
    suspend fun searchAccommodation(
        @Query("number") page: String,
        @Query("limit") limit: String,
        @Query("companyName") companyName: String
    ): Response<AccommodationResponse>

    /**
     * 카셰어링 With 쏘카
     */
    @GET("tta9at0b01a0181t11bttttt9b0tt9tt/3err3be_jt2oeto22rtt_932291tp__2")
    suspend fun searchCarsharingWithSocar(
        @Query("number") page: String,
        @Query("limit") limit: String
    ): Response<CarsharingResponse>

    /**
     * 카셰어링
     */
    @GET("88D0ba0a01a08D081tt8aDba21aabt28/3err3be_jt2oeto22rtt_932291tp__2")
    suspend fun searchCarsharing(
        @Query("number") page: String,
        @Query("limit") limit: String,
    ): Response<CarsharingResponse>
}