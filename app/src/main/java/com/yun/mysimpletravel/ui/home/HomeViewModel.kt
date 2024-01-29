package com.yun.mysimpletravel.ui.home

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.LocationConstants
import com.yun.mysimpletravel.common.constants.NavigationConstants
import com.yun.mysimpletravel.data.model.travel.accommodation.AccommodationModel
import com.yun.mysimpletravel.data.model.travel.carsharing.CarsharingModel
import com.yun.mysimpletravel.data.model.weather.NowWeatherModel
import com.yun.mysimpletravel.data.repository.jejuhub.JejuHubRepositoryImpl
import com.yun.mysimpletravel.data.repository.weather.WeatherRepositoryImpl
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil,
    private val jejuHubRepositoryImpl: JejuHubRepositoryImpl,
    private val weatherRepositoryImpl: WeatherRepositoryImpl
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>(false)
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isWeatherLoading = MutableLiveData<Boolean>(false)
    val isWeatherLoading: LiveData<Boolean> get() = _isWeatherLoading

    private val _moveScreen = MutableLiveData<String>()
    val moveScreen: LiveData<String> get() = _moveScreen

    private val _nowWeather = MutableLiveData<NowWeatherModel>()
    val nowWeather: LiveData<NowWeatherModel> get() = _nowWeather

    init {
        nowWeather()
    }

    /**
     * 현재 날씨
     * 네이버 크롤링 사용
     */
    private fun nowWeather() {
        setWeatherLoading(true)
        val location = sPrefs.getString(mContext, LocationConstants.Key.NAME)

        if (location.isNullOrEmpty()) {
            setLoading(false)
            setWeatherLoading(false)
            return
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    setWeatherLoading(true)
                    weatherRepositoryImpl.nowWeather(
                        BuildConfig.WEATHER_URL,
                        location
                    ) { data, throwable ->
                        if (throwable != null) throw Exception(throwable)
                        data?.let {
                            setNowWeather(it)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    setWeatherLoading(false)
                }
            }
        }
    }

    private fun setNowWeather(weather: NowWeatherModel) {
        _nowWeather.postValue(weather)
    }

    private fun setLoading(loading: Boolean) {
        Log.d("lys", "setLoading > $loading")
        _isLoading.postValue(loading)
    }

    private fun setWeatherLoading(loading: Boolean) {
        _isWeatherLoading.postValue(loading)
    }

    fun onWeatherClick(v: View) {
        if (sPrefs.getString(mContext, LocationConstants.Key.NAME).isNullOrEmpty()) {
            moveScreens(NavigationConstants.MainScreen.SETTING)
        } else {
            nowWeather()
        }
    }

//    fun searchAccommodation(v: View) {
//        viewModelScope.launch {
//            try {
//                setLoading(true)
//                jejuHubRepositoryImpl.invoke(
//                    "1",
//                    "",
//                    object : JejuHubRepositoryImpl.GetDataCallBack<AccommodationModel> {
//                        override fun onSuccess(data: AccommodationModel) {
//                            Log.d("lys", "searchAccommodation > ${data}")
//                            moveScreens(NavigationConstants.MainScreen.SETTING)
//                        }
//
//                        override fun onFailure(throwable: Throwable) {
//                            throwable.printStackTrace()
//                            Toast.makeText(mContext, "_서버와의 통신이 원활하지 않습니다", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    })
//            } catch (e: Exception) {
//                Log.e("lys", "error >>> ${e.message}")
//                e.printStackTrace()
//            } finally {
//                setLoading(false)
//                Log.d("lys", "Finally block executed.")
//            }
//        }
//    }

    fun searchAccommodation(v: View) {
        viewModelScope.launch {
            try {
                setLoading(true)
                jejuHubRepositoryImpl.invoke(
                    "1",
                    ""
                ) { data, throwable ->
                    if (throwable != null) {
                        throwable.printStackTrace()
                        return@invoke
                    }
                    Log.d("lys", "searchAccommodation > ${data}")
                    moveScreens(NavigationConstants.MainScreen.SETTING)
                }
            } catch (e: Exception) {
                Log.e("lys", "error >>> ${e.message}")
                e.printStackTrace()
            } finally {
                setLoading(false)
                Log.d("lys", "Finally block executed.")
            }
        }
    }

    fun moveScreens(screen: String) {
        _moveScreen.postValue(screen)
    }
}