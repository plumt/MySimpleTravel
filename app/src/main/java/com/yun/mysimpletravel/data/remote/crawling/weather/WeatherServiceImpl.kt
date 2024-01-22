package com.yun.mysimpletravel.data.remote.crawling.weather

import com.yun.mysimpletravel.data.dto.AccommodationResponse
import com.yun.mysimpletravel.data.dto.NowWeatherResponse
import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationList
import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationModel
import com.yun.mysimpletravel.data.model.weather.NowWeatherModel

class WeatherServiceImpl {
    fun NowWeatherResponse.toNowWeatherModel(): NowWeatherModel {
        return NowWeatherModel(
            location = location,
            weatherState = weatherState,
            weatherTemperature = weatherTemperature,
            weatherImagePath = weatherImagePath,
            weatherDetail = weatherDetail,
            weatherDust = weatherDust,
            weatherUDust = weatherUDust,
            weatherUV = weatherUV,
            weatherCompare = weatherCompare
        )
    }
}