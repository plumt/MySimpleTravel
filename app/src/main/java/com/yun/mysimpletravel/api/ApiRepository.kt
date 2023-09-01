package com.yun.mysimpletravel.api

import javax.inject.Inject

class ApiRepository @Inject constructor(private val api: Api) {

    suspend fun srtNcst(nx: String, ny: String, baseDate: String, baseTime: String) =
        api.srtNcst(nx, ny, baseDate, baseTime)

    suspend fun srtFcst(nx: String, ny: String, baseDate: String, baseTime: String) =
        api.srtFcst(nx, ny, baseDate, baseTime)

    suspend fun fcst(nx: String, ny: String, baseDate: String, baseTime: String) =
        api.fcst(nx, ny, baseDate, baseTime)
}