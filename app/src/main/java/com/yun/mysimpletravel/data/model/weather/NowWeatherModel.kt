package com.yun.mysimpletravel.data.model.weather

data class NowWeatherModel(
    val location: String,
    val weatherState: String,
    val weatherTemperature: String,
    val weatherImagePath: String,
    val weatherDetail: String,
    val weatherDust: String,
    val weatherUDust: String,
    val weatherUV: String,
    val weatherCompare: String
)
