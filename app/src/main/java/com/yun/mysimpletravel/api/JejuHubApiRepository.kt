package com.yun.mysimpletravel.api

import javax.inject.Inject

class JejuHubApiRepository @Inject constructor(private val api: Api) {


    suspend fun searchAccommodation(page: String, companyName: String) =
        api.searchAccommodation(page, "100", companyName)
}
