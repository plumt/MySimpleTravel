package com.yun.mysimpletravel.api

import javax.inject.Inject

class ApiRepository @Inject constructor(private val api: Api) {

    suspend fun nowWeather(nx: String, ny: String, baseDate: String, baseTime: String) =
        api.nowWeather(nx, ny, baseDate, baseTime)
}