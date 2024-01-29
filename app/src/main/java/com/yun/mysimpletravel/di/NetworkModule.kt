package com.yun.mysimpletravel.di

import android.content.Context
import com.yun.mysimpletravel.data.remote.ApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val connectTimeout: Long = 5000
    private const val readTimeout: Long = 5000
    private const val cacheSize = 10 * 1024 * 1024 // 10 MB (캐시 크기)

    @Singleton
    @Provides
    fun provideHttpClient(
//        sharedPreferences: PreferenceUtil,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val cache = Cache(context.cacheDir, cacheSize.toLong()) // 캐시 디렉토리와 크기 설정
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor(context))
//            .addNetworkInterceptor(ApiInterceptor(context))
            .cache(cache)
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

}