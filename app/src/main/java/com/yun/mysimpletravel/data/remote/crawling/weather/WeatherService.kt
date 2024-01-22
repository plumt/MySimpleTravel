package com.yun.mysimpletravel.data.remote.crawling.weather

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.http.Query

object WeatherService {
    suspend fun nowWeather(
        url: String,
        location: String
    ) = Jsoup.connect("${url}${location}날씨").get()
}