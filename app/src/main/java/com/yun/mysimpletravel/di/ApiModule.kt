package com.yun.mysimpletravel.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.data.remote.api.jejuhub.JejuHubService
import com.yun.mysimpletravel.data.remote.api.location.LocationService
import com.yun.mysimpletravel.data.repository.jejuhub.JejuHubRepository
import com.yun.mysimpletravel.data.repository.location.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private fun provideRetrofit(client: OkHttpClient, baseUrl: String) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideJejuHubApiRepository(
        @ApplicationContext context: Context,
        client: OkHttpClient
    ): JejuHubRepository {
        val retrofit = provideRetrofit(client, BuildConfig.JEJU_HUB_URL)
        return JejuHubRepository(retrofit.create(JejuHubService::class.java))
    }

    @Provides
    @Singleton
    fun provideLocationApiRepository(
        @ApplicationContext context: Context,
        client: OkHttpClient
    ): LocationRepository {
        val retrofit = provideRetrofit(client, BuildConfig.LOCATION_URL)
        return LocationRepository(retrofit.create(LocationService::class.java))
    }
}