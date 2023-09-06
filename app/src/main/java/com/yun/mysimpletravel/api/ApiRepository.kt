package com.yun.mysimpletravel.api

import javax.inject.Inject

class ApiRepository @Inject constructor(private val api: Api) {

    suspend fun searchLocationCode(regcode_pattern: String) = api.searchLocationCode(regcode_pattern)
}