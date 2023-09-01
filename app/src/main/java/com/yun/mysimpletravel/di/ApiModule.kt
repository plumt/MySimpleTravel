package com.yun.mysimpletravel.di

import com.yun.mysimpletravel.api.Api
import com.yun.mysimpletravel.api.ApiRepository
import com.yun.mysimpletravel.common.constants.ApiConstants.ApiType.LOCATION
import com.yun.mysimpletravel.common.constants.ApiConstants.ApiType.WEATHER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Named(WEATHER)
    @Singleton
    @Provides
    fun providerWeatherApi(@Named(WEATHER) retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Named(WEATHER)
    @Singleton
    @Provides
    fun providerWeatherRepository(@Named(WEATHER)api: Api) = ApiRepository(api)

    @Named(LOCATION)
    @Singleton
    @Provides
    fun providerLocationApi(@Named(LOCATION) retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Named(LOCATION)
    @Singleton
    @Provides
    fun providerLocationRepository(@Named(LOCATION)api: Api) = ApiRepository(api)
}