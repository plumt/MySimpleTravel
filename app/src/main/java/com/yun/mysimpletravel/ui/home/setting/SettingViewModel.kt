package com.yun.mysimpletravel.ui.home.setting

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
class SettingViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>(false)
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _userInfo = MutableLiveData<UserInfoDataModel>()
    val userInfo: LiveData<UserInfoDataModel> get() = _userInfo

    init {
        setUserInfo()
    }

    private fun setUserInfo() {
        _userInfo.value = UserInfoDataModel(
            userId = sPrefs.getString(mContext, SNS_ID)!!,
            userName = sPrefs.getString(mContext, NAME)!!,
            userProfileUrl = sPrefs.getString(mContext, PROFILE),
            loginType = sPrefs.getString(mContext, TYPE)!!
        )
    }
}