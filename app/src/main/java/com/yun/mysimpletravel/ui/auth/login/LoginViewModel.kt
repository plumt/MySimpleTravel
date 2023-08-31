package com.yun.mysimpletravel.ui.auth.login

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
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>(false)
    override val isLoading: LiveData<Boolean> get() = _isLoading

    /**
     * 로그인 유저 정보 저장
     */
    fun saveUserInfo(info: UserInfoDataModel){
        sPrefs.setString(mContext,SNS_ID,info.userId)
        sPrefs.setString(mContext,NAME,info.userName)
        sPrefs.setString(mContext,PROFILE,info.userProfileUrl)
        sPrefs.setString(mContext, TYPE,info.loginType)
    }

}