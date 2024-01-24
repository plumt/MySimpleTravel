package com.yun.mysimpletravel.di

import com.yun.mysimpletravel.data.remote.crawling.weather.WeatherService
import com.yun.mysimpletravel.data.remote.crawling.weather.WeatherServiceImpl
import com.yun.mysimpletravel.data.repository.weather.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CrawlingModule {

    @Provides
    @Singleton
    fun provideWeatherService(): WeatherService {
        return WeatherService
    }
}