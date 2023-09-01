package com.yun.mysimpletravel.ui.home.travel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.api.Api
import com.yun.mysimpletravel.api.ApiRepository
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.ApiConstants.ApiType.WEATHER
import com.yun.mysimpletravel.di.ApiModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            callNowWeatherApi()
        }
    }

    /**
     * 현재 날씨
     */
    private suspend fun callNowWeatherApi(){
        val response = callApi({weatherApi.nowWeather("55","127","20230901","0900")})
        Log.d("lys","response > $response")
    }
}