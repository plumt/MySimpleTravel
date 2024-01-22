package com.yun.mysimpletravel.data.repository.weather

import com.yun.mysimpletravel.data.remote.crawling.weather.WeatherService
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService
){
    suspend fun nowWeather(url: String, location: String) = weatherService.nowWeather(url, location)
}