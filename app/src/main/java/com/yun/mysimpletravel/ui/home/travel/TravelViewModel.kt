package com.yun.mysimpletravel.ui.home.travel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.api.ApiRepository
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.ApiConstants.ApiType.WEATHER
import com.yun.mysimpletravel.common.constants.WeatherConstants.Category.POP
import com.yun.mysimpletravel.common.constants.WeatherConstants.Category.PTY
import com.yun.mysimpletravel.common.constants.WeatherConstants.Category.REH
import com.yun.mysimpletravel.common.constants.WeatherConstants.Category.SKY
import com.yun.mysimpletravel.common.constants.WeatherConstants.Category.T1H
import com.yun.mysimpletravel.common.constants.WeatherConstants.Category.VEC
import com.yun.mysimpletravel.common.constants.WeatherConstants.Category.WSD
import com.yun.mysimpletravel.common.constants.WeatherConstants.Pty.NOT
import com.yun.mysimpletravel.common.constants.WeatherConstants.Pty.RAIN
import com.yun.mysimpletravel.common.constants.WeatherConstants.Pty.RAIN_SNOW
import com.yun.mysimpletravel.common.constants.WeatherConstants.Pty.SHOWER
import com.yun.mysimpletravel.common.constants.WeatherConstants.Pty.SNOW
import com.yun.mysimpletravel.common.constants.WeatherConstants.Sky.CLOUDY
import com.yun.mysimpletravel.common.constants.WeatherConstants.Sky.OVERCAST
import com.yun.mysimpletravel.common.constants.WeatherConstants.Sky.SUNNY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TravelViewModel @Inject constructor(
    application: Application,
    @Named(WEATHER) private val weatherApi: ApiRepository
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        Log.d("lys", "TravelViewModel")
    }

    /**
     * 현재 날씨 > 단기 예보
     */
    suspend fun callNowWeatherApi(): String {
        val response = callApi({ weatherApi.srtFcst("55", "127", "20230901", "1400") })
        Log.d("lys", "response > $response")
        var result = ""
        val filter = arrayListOf<String>()
        response?.let { res ->
            res.response.body?.items?.item?.forEachIndexed { _, item ->
                if (filter.find { it == item.category } != null) return@forEachIndexed
                filter.add(item.category)
                when (item.category) {
                    T1H -> {
                        // 온도
                        result += "온도 : ${item.fcstValue}\n"
                    }

                    REH -> {
                        // 습도
                        result += "습도 : ${item.fcstValue}\n"
                    }

                    VEC -> {
                        // 풍향
                        result += "풍향 : ${item.fcstValue}\n"
                    }

                    WSD -> {
                        // 풍속
                        result += "풍속 : ${item.fcstValue}\n"
                    }

                    POP -> {
                        // 강수 확률
                        result += "강수확률 : ${item.fcstValue}\n"
                    }

                    PTY -> {
                        // 강수형태
                        val pty = when (item.fcstValue) {
                            NOT -> "없음"
                            RAIN -> "비"
                            RAIN_SNOW -> "비/눈"
                            SNOW -> "눈"
                            SHOWER -> "소나기"
                            else -> ""
                        }
                        result += "강수형태 : $pty\n"
                    }

                    SKY -> {
                        val sky = when (item.fcstValue) {
                            SUNNY -> "맑음"
                            CLOUDY -> "구름 많음"
                            OVERCAST -> "흐림"
                            else -> ""
                        }
                        result += "하늘상태 : $sky\n"
                    }
                }
            }
        }
        return result
    }


}