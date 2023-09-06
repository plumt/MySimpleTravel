package com.yun.mysimpletravel.common.manager

import android.content.Context
import android.util.Log
import com.yun.mysimpletravel.common.constants.AuthConstants
import com.yun.mysimpletravel.common.constants.LocationConstants
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.util.PreferenceUtil
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(
    private val context: Context,
    private val sPrefs: PreferenceUtil
) {

    /**
     * 회원탈퇴 및 로그아웃으로 유저 정보 초기화
     */
    fun removeUserInfo() {
        sPrefs.setString(context, AuthConstants.Info.SNS_ID, "")
        sPrefs.setString(context, AuthConstants.Info.NAME, "")
        sPrefs.setString(context, AuthConstants.Info.PROFILE, "")
        sPrefs.setString(context, AuthConstants.Info.TYPE, "")
    }

    /**
     * 로그인 유저 정보 저장
     */
    fun setUserInfo(info: UserInfoDataModel?) {
        if (info == null) return
        sPrefs.setString(context, AuthConstants.Info.SNS_ID, info.userId)
        sPrefs.setString(context, AuthConstants.Info.NAME, info.userName)
        sPrefs.setString(context, AuthConstants.Info.PROFILE, info.userProfileUrl)
        sPrefs.setString(context, AuthConstants.Info.TYPE, info.loginType)
    }

    fun updateLocation(name: String?, fullName: String?) {
        if (name == null || fullName == null) return
        sPrefs.setString(context, LocationConstants.Key.NAME, name)
        sPrefs.setString(context, LocationConstants.Key.FULL_NAME, fullName)
    }

}