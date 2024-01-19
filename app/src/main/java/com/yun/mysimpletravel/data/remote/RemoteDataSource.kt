package com.yun.mysimpletravel.data.remote

import retrofit2.Response

interface RemoteDataSource {
    suspend fun <T> callApi(
        api: Response<T>,
        onResponse: (T) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}