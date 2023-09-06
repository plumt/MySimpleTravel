package com.yun.mysimpletravel.ui.home.travel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.LocationConstants
import com.yun.mysimpletravel.data.model.weather.NowWeatherDataModel
import com.yun.mysimpletravel.util.PreferenceUtil
import com.yun.mysimpletravel.util.WeatherUtil.weatherIcon
import com.yun.mysimpletravel.util.WeatherUtil.weatherDetail
import com.yun.mysimpletravel.util.WeatherUtil.weatherState
import com.yun.mysimpletravel.util.WeatherUtil.weatherTemperature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>(true)
    override val isLoading: LiveData<Boolean> get() = _isLoading

    /**
     * 현재 날씨
     * 네이버 크롤링 사용
     */
    suspend fun nowWeather(): NowWeatherDataModel.WeatherInfo? {
        val url = BuildConfig.WEATHER_URL
        val location = sPrefs.getString(mContext, LocationConstants.Key.FULL_NAME)
        if (location.isNullOrEmpty()) return null
        val result = withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(url + location + "날씨").get()
            val currentWeather = doc.weatherState()
            val currentTemperature = doc.weatherTemperature()
            val currentImagePath = doc.weatherIcon()
            val currentWeatherDetail = doc.weatherDetail()
            NowWeatherDataModel.WeatherInfo(currentWeather,currentTemperature,currentImagePath,currentWeatherDetail)
        }

        // 결과를 처리
        Log.d("lys", "testWeather 현재 날씨 > ${result.currentWeather}")
        Log.d("lys", "testWeather 현재 온도 > ${result.currentTemperature}")
        Log.d("lys", "testWeather 현재 상태 > ${result.currentWeatherDetail}")
        Log.d("lys", "testWeather 이미지 > ${result.currentImagePath}")
        setLoading(false)
        return result
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}