package com.yun.mysimpletravel.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.api.ApiRepository
import com.yun.mysimpletravel.api.JejuHubApiRepository
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.ApiConstants.ApiType.JEJU_DATA_HUB
import com.yun.mysimpletravel.common.constants.LocationConstants
import com.yun.mysimpletravel.data.model.weather.NowWeatherDataModel
import com.yun.mysimpletravel.util.PreferenceUtil
import com.yun.mysimpletravel.util.WeatherUtil.weatherCompare
import com.yun.mysimpletravel.util.WeatherUtil.weatherDetail
import com.yun.mysimpletravel.util.WeatherUtil.weatherDust
import com.yun.mysimpletravel.util.WeatherUtil.weatherIcon
import com.yun.mysimpletravel.util.WeatherUtil.weatherState
import com.yun.mysimpletravel.util.WeatherUtil.weatherTemperature
import com.yun.mysimpletravel.util.WeatherUtil.weatherUDust
import com.yun.mysimpletravel.util.WeatherUtil.weatherUV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil,
    private val jejuHubApi: JejuHubApiRepository
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>(true)
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isWeatherLoading = MutableLiveData<Boolean>(false)
    val isWeatherLoading: LiveData<Boolean> get() = _isWeatherLoading

    private val _nowWeather = MutableLiveData<NowWeatherDataModel.WeatherInfo>()
    val nowWeather: LiveData<NowWeatherDataModel.WeatherInfo> get() = _nowWeather

    init {
        setLoading(false)
    }

    /**
     * 현재 날씨
     * 네이버 크롤링 사용
     */
    suspend fun nowWeather(): Boolean {
        setWeatherLoading(true)
        val location = sPrefs.getString(mContext, LocationConstants.Key.FULL_NAME)
        if (location.isNullOrEmpty()) {
            setLoading(false)
            setWeatherLoading(false)
            return false
        }

        return try {
            val doc = withContext(Dispatchers.IO) {
                Jsoup.connect("${BuildConfig.WEATHER_URL}${location}날씨").get()
            }
            Log.d("weather", "doc > $doc")
            setWeatherLoading(false)
            setNowWeather(
                NowWeatherDataModel.WeatherInfo(
                    location = sPrefs.getString(mContext, LocationConstants.Key.FULL_NAME) ?: "-",
                    weatherState = doc.weatherState(),
                    weatherTemperature = doc.weatherTemperature(),
                    weatherImagePath = doc.weatherIcon(),
                    weatherDetail = doc.weatherDetail(),
                    weatherDust = doc.weatherDust(),
                    weatherUDust = doc.weatherUDust(),
                    weatherUV = doc.weatherUV(),
                    weatherCompare = doc.weatherCompare()
                )
            ).also { setLoading(false) }
            true
        } catch (e: Exception) {
            setWeatherLoading(false)
            e.printStackTrace()
            false
        }
    }

    private fun setNowWeather(weather: NowWeatherDataModel.WeatherInfo) {
        _nowWeather.value = weather
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    private fun setWeatherLoading(loading: Boolean) {
        _isWeatherLoading.value = loading
    }


    suspend fun searchAccommodation(){
        val response = callApi({jejuHubApi.searchAccommodation("1","")},1)
        Log.d("lys","searchAccommodation > $response")
    }

}