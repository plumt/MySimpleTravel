package com.yun.mysimpletravel.data.remote.api.jejuhub

import com.yun.mysimpletravel.data.dto.AccommodationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JejuHubService {

    /**
     * 관광 숙박업 정보
     */
//    @Headers("Cache-Control: public, max-stale=10")
    @GET("/api/proxy/ttbttaD3aaa3ta8bbtDtaDD18t37aD17/3err3be_jt2oeto22rtt_932291tp__2")
    suspend fun searchAccommodation(
        @Query("page") page: String,
        @Query("limit") limit: String,
        @Query("companyName") companyName: String
    ): Response<AccommodationResponse>
}