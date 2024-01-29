package com.yun.mysimpletravel.data.remote

import android.content.Context
import android.util.Log
import com.yun.mysimpletravel.util.Util.openApiCheck
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class ApiInterceptor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()

        if (openApiCheck(originalRequest.url.toString())) {
            // open api라면, 캐시를 1일로 한다
//            60 * 60 * 24 * 1
            val cacheControl = CacheControl.Builder()
                .maxAge(1,TimeUnit.DAYS)
                .build()
            newRequest.removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control",cacheControl.toString())
//            newRequest.header("Cache-Control", "public, max-stale=" + 60 * 60 * 24 * 1).removeHeader("Pragma")
        }
        val response = chain.proceed(newRequest.build())
        return response
    }
}