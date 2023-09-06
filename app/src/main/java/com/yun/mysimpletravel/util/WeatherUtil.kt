package com.yun.mysimpletravel.util

import com.yun.mysimpletravel.BuildConfig
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
}