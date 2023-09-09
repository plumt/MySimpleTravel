package com.yun.mysimpletravel.ui.home.travel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.LocationConstants
import com.yun.mysimpletravel.data.model.weather.NowWeatherDataModel
import com.yun.mysimpletravel.util.PreferenceUtil
import com.yun.mysimpletravel.util.WeatherUtil.weatherIcon
import com.yun.mysimpletravel.util.WeatherUtil.weatherDetail
import com.yun.mysimpletravel.util.WeatherUtil.weatherDust
import com.yun.mysimpletravel.util.WeatherUtil.weatherState
import com.yun.mysimpletravel.util.WeatherUtil.weatherTemperature
import com.yun.mysimpletravel.util.WeatherUtil.weatherUDust
import com.yun.mysimpletravel.util.WeatherUtil.weatherUV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.lang.Exception
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
        val location = sPrefs.getString(mContext, LocationConstants.Key.FULL_NAME)
        if (location.isNullOrEmpty()) {
            setLoading(false)
            return null
        }

        return try {
            val doc = withContext(Dispatchers.IO) {
                Jsoup.connect("${BuildConfig.WEATHER_URL}${location}날씨").get()
            }

            NowWeatherDataModel.WeatherInfo(
                doc.weatherState(),
                doc.weatherTemperature(),
                doc.weatherIcon(),
                doc.weatherDetail(),
                doc.weatherDust(),
                doc.weatherUDust(),
                doc.weatherUV()
            ).also { setLoading(false) }
        } catch (e: Exception) {
            setLoading(false)
            e.printStackTrace()
            null
        }
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}