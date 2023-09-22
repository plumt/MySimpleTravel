package com.yun.mysimpletravel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    // 로딩 프로그레스바 변수
    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    // 바텀 네비게이션바
    private val _isBottomNav = MutableLiveData<Boolean>(false)
    val isBottomNav: LiveData<Boolean> get() = _isBottomNav

    // 바텀 네비게이션바
    private val _bottomNavDoubleTab = MutableLiveData<Boolean>(false)
    val bottomNavDoubleTab: LiveData<Boolean> get() = _bottomNavDoubleTab

    /**
     * 로딩 프로그레스바 노출/숨김
     */
    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    /**
     * 바텀 네비게이션 노출/숨김
     */
    fun setBottomNav(isVisible: Boolean) {
        _isBottomNav.value = isVisible
    }

    fun bottomNavDoubleTabEvent(clear: Boolean = false){
        _bottomNavDoubleTab.value = clear
    }

}