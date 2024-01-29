package com.yun.mysimpletravel.data.repository.weather

import com.yun.mysimpletravel.data.model.weather.NowWeatherModel
import com.yun.mysimpletravel.data.remote.GetDataCallBack
import com.yun.mysimpletravel.util.WeatherUtil.weatherCompare
import com.yun.mysimpletravel.util.WeatherUtil.weatherDetail
import com.yun.mysimpletravel.util.WeatherUtil.weatherDust
import com.yun.mysimpletravel.util.WeatherUtil.weatherIcon
import com.yun.mysimpletravel.util.WeatherUtil.weatherState
import com.yun.mysimpletravel.util.WeatherUtil.weatherTemperature
import com.yun.mysimpletravel.util.WeatherUtil.weatherUDust
import com.yun.mysimpletravel.util.WeatherUtil.weatherUV
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

//    interface GetDataCallBack<T> {
//        fun onSuccess(data: T)
//        fun onFailure(throwable: Throwable)
//    }

    suspend fun nowWeather(
        url: String, location: String,
        callBack: GetDataCallBack<NowWeatherModel>
    ) {
        try {
            val doc = weatherRepository.nowWeather(url, location)
            val data = NowWeatherModel(
                location = location,
                weatherState = doc.weatherState(),
                weatherTemperature = doc.weatherTemperature(),
                weatherImagePath = doc.weatherIcon(),
                weatherDetail = doc.weatherDetail(),
                weatherDust = doc.weatherDust(),
                weatherUDust = doc.weatherUDust(),
                weatherUV = doc.weatherUV(),
                weatherCompare = doc.weatherCompare()
            )
            callBack.invoke(data, null)
        } catch (e: Exception) {
            callBack.invoke(null, e)
        }
    }
}