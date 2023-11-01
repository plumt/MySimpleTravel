package com.yun.mysimpletravel.util

import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.common.constants.WeatherConstants
import org.jsoup.nodes.Document

object WeatherUtil {

    /**
     * 날씨 아이콘
     */
    fun Document.weatherIcon(): String {
        val result = select(".weather_main i")
        val baseUrl = BuildConfig.WEATHER_IMG_URL
        return result.firstOrNull()?.let { element ->
            val url = element.className()
            val urlConvert = url.replace("wt_", "").replace("ico_", "flat_").replace(" ", "_") + ".svg"
            baseUrl + urlConvert
        } ?: ""
    }

    /**
     * 날씨 상태
     */
    fun Document.weatherState() = select(".weather_main").firstOrNull()?.text() ?: ""

    /**
     * 날씨 온도
     */
    fun Document.weatherTemperature() = select(".temperature_text").firstOrNull()?.text() ?: ""

    /**
     * 날씨 상세
     */
    fun Document.weatherDetail() =
        select(".temperature_info .summary_list").firstOrNull()?.text() ?: ""

    fun Document.weatherDust() = select(".today_chart_list .item_today")?.get(0)?.text()?:""
    fun Document.weatherUDust() = select(".today_chart_list .item_today")?.get(1)?.text()?:""

    fun Document.weatherUV() = select(".today_chart_list .item_today")?.get(2)?.text()?:""

    /**
     * 어제보다 높거나 낮은 정도
     */
    fun Document.weatherCompare() = select(".temperature_info .temperature").firstOrNull()?.text() ?: ""

    /**
     * 미세먼지 상태
     */
    fun dustCheck(str: String?): Any {
        return if (str == null) 0
        else when {
            str.contains("좋음") -> WeatherConstants.State.GOOD
            str.contains("보통") -> WeatherConstants.State.NOMAL
            str.contains("매우") -> WeatherConstants.State.WORST
            str.contains("나쁨") -> WeatherConstants.State.BAD
            else -> 0
        }
    }

    /**
     * 자외선 상태
     */
    fun uvCheck(str: String?): Any {
        return if (str == null) 0
        else when {
            str.contains("좋음") -> WeatherConstants.State.GOOD
            str.contains("보통") -> WeatherConstants.State.NOMAL
            str.contains("매우") -> WeatherConstants.State.WORST
            str.contains("높음") -> WeatherConstants.State.BAD
            else -> 0
        }
    }
}