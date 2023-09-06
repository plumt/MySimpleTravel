package com.yun.mysimpletravel.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.common.constants.ApiConstants.ApiType.LOCATION
import com.yun.mysimpletravel.common.constants.ApiConstants.ApiType.WEATHER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val connectTimeout: Long = 60
    private const val readTimeout: Long = 60

    @Singleton
    @Provides
    fun provideHttpClient(
//        sharedPreferences: PreferenceUtil,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
//            .addInterceptor(AccessTokenInterceptor(sharedPreferences,context))
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    private fun provideRetrofit(client: OkHttpClient, baseUrl: String) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    @Named(LOCATION)
    @Provides
    @Singleton
    fun provideLocationRetrofit(
        client: OkHttpClient,
        @ApplicationContext context: Context
    ): Retrofit = provideRetrofit(client, BuildConfig.LOCATION_URL)

    @Named("test")
    @Provides
    @Singleton
    fun provideTestRetrofit(
        client: OkHttpClient,
        @ApplicationContext context: Context
    ): Retrofit = provideRetrofit(client, "")

}