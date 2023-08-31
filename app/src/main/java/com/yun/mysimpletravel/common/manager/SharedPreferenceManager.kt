package com.yun.mysimpletravel.common.manager

import android.content.Context
import com.yun.mysimpletravel.MySimpleTravelApplication
import com.yun.mysimpletravel.common.constants.AuthConstants
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.NAME
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.PROFILE
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.SNS_ID
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.TYPE
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.util.PreferenceUtil
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(
    private val context: Context,
    private val sPrefs: PreferenceUtil
) {
    fun removeUserInfo() {
        sPrefs.setString(context, SNS_ID, "")
        sPrefs.setString(context, NAME, "")
        sPrefs.setString(context, PROFILE, "")
        sPrefs.setString(context, TYPE, "")
    }

    fun setUserInfo(info: UserInfoDataModel) {
        sPrefs.setString(context, SNS_ID, info.userId)
        sPrefs.setString(context, NAME, info.userName)
        sPrefs.setString(context, PROFILE, info.userProfileUrl)
        sPrefs.setString(context, TYPE, info.loginType)
    }

}