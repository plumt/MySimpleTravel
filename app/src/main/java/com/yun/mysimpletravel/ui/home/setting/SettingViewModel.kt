package com.yun.mysimpletravel.ui.home.setting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.base.ListLiveData
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.NAME
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.PROFILE
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.SNS_ID
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.TYPE
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.APP_VERSION
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.LOG_OUT
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.SIGN_OUT
import com.yun.mysimpletravel.data.model.setting.SettingDataModel
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _userInfo = MutableLiveData<UserInfoDataModel>()
    val userInfo: LiveData<UserInfoDataModel> get() = _userInfo

    private val _settingList = ListLiveData<SettingDataModel>()
    val settingList: ListLiveData<SettingDataModel> get() = _settingList

    init {
        setUserInfo()
        setSettingList()
        //TODO 서버 앱 최신 버전을 가져와야 함
    }

    /**
     * 유저 정보 불러오기
     */
    private fun setUserInfo() {
        _userInfo.value = UserInfoDataModel(
            userId = sPrefs.getString(mContext, SNS_ID)!!,
            userName = sPrefs.getString(mContext, NAME)!!,
            userProfileUrl = sPrefs.getString(mContext, PROFILE),
            loginType = sPrefs.getString(mContext, TYPE)!!
        )
    }

    private fun setSettingList() {
        val appVersion = SettingDataModel(
            APP_VERSION,
            "앱 버전 ${BuildConfig.VERSION_NAME}",
            "업데이트"
        )
        val logOut = SettingDataModel(
            LOG_OUT,
            "로그아웃",
            ""
        )
        val signOut = SettingDataModel(
            SIGN_OUT,
            "회원탈퇴",
            ""
        )

        _settingList.add(appVersion)
        _settingList.add(logOut)
        _settingList.add(signOut)
    }
}