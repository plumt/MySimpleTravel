package com.yun.mysimpletravel.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.TRAVEL
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _screen = MutableLiveData<Int>(TRAVEL)
    val screen: LiveData<Int> get() = _screen

    fun setScreen(position: Int){
        _screen.value = position
    }
}