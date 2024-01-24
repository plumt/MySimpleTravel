package com.yun.mysimpletravel

import android.app.Application
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.NavigationConstants
import com.yun.mysimpletravel.common.manager.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Objects
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


    private val _bottomIndex = MutableLiveData<Int>(2)
    val bottomIndex: LiveData<Int> get() = _bottomIndex


    private val _moveScreen = MutableLiveData<Int>(R.id.fab_home)
    val moveScreen: LiveData<Int> get() = _moveScreen

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

    fun test(): Boolean {
        return true
    }

    fun bottomNavTabEvent(index: Any): Boolean {
        val destinationId = when (index) {
            R.id.map -> R.id.global_settingFragment
            R.id.diary -> R.id.global_diaryFragment
            R.id.community -> R.id.action_global_communityFragment
            R.id.setting -> R.id.global_settingFragment
            R.id.fab_home -> R.id.global_homeFragment
            else -> return false
        }
        _moveScreen.postValue(destinationId)
        return true
    }

    fun bottomNavDoubleTabEvent(clear: Boolean = false) {
        _bottomNavDoubleTab.value = clear
    }

    fun getIconResource(): Int {
        return if (moveScreen.value == R.id.fab_home) {
            R.drawable.baseline_home_24_white
        } else {
            R.drawable.baseline_home_24_black
        }
    }

    fun fabHomeId() = R.id.fab_home

}