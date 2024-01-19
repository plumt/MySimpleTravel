package com.yun.mysimpletravel.data.remote

import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource {
    override suspend fun <T> callApi(
        api: Response<T>,
        onResponse: (T) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val maxRetries = 3
        var retries = 0
        while (retries < maxRetries) {
            if (api.isSuccessful && api.body() != null) {
                onResponse(api.body()!!)
                break
            } else {
                if (retries == maxRetries - 1) {
                    val errorBody = api.errorBody()?.string() ?: "Unknown error"
                    onFailure(Throwable("API call failed with error : $errorBody"))
                    break
                }
                retries++
            }
        }
    }
}


/**
 * suspend fun <T : Response<R>, R> callApi(api: suspend () -> T, maxRetries: Int = 3): R? {
if (!isNetworkConnected(mContext)) return null
var retries = 0
while (retries < maxRetries) {
try {
val apiResponse = api()
if (apiResponse.isSuccessful) return apiResponse.body()
else Log.e("lys", "ApiResponse Error $retries")
} catch (e: Exception) {
Log.e("lys", "Coroutine Exception: $e")
}
retries++
}
return null
}
 */