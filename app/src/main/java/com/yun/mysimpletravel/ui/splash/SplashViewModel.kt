package com.yun.mysimpletravel.ui.splash

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.NAME
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.PROFILE
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.SNS_ID
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.TYPE
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil
) : BaseViewModel(application) {

    // 로딩 프로그레스바 변수
    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    val startingAppTime: LocalDateTime = LocalDateTime.now()

    /**
     * 자동 로그인 여부
     * @return true 로그인 이력 있음
     * @return false 로그인 이력 없음
     */
    fun automaticLoginStatus(): Boolean = !sPrefs.getString(mContext, SNS_ID).isNullOrEmpty()

    /**
     * 로그인 유저 정보 저장
     */
    fun saveUserInfo(info: UserInfoDataModel) {
        sPrefs.setString(mContext, SNS_ID, info.userId)
        sPrefs.setString(mContext, NAME, info.userName)
        sPrefs.setString(mContext, PROFILE, info.userProfileUrl)
        sPrefs.setString(mContext, TYPE, info.loginType)
    }
}