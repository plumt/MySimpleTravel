package com.yun.mysimpletravel.ui.home.travel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.api.ApiRepository
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.ApiConstants.ApiType.WEATHER
import com.yun.mysimpletravel.common.constants.LocationConstants
import com.yun.mysimpletravel.data.model.weather.NowWeatherDataModel
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TravelViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil,
    @Named(WEATHER) private val weatherApi: ApiRepository
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>(true)
    override val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        Log.d("lys", "TravelViewModel")
    }

    suspend fun testWeather(): NowWeatherDataModel.WeatherInfo? {
        val url =
            "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query="
//            "https://search.naver.com/search.naver?where=nexearch&amp;sm=top_hty&amp;fbm=1&amp;ie=utf8&amp;query="
        val location = sPrefs.getString(mContext, LocationConstants.Key.FULL_NAME)
        if (location.isNullOrEmpty()) return null
        val result = withContext(Dispatchers.IO) {
            // 네트워크 작업 수행
            val doc = Jsoup.connect(url + location + "날씨").get()
            val currentWeather = doc.select(".weather_main")[0].text()
            val currentTemperature = doc.select(".temperature_text")[0].text()
            val currentImagePath = setImagePath(doc.select(".weather_main i")[0].className())
            val currentWeatherDetail = doc.select(".temperature_info .summary_list")[0].text()

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

    private fun setImagePath(url: String): String =
        "https://ssl.pstatic.net/sstatic/keypage/outside/scui/weather_new_new/img/weather_svg/${
            url.replace("wt_", "").replace("ico_", "flat_").replace(" ", "_")
        }.svg"

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}