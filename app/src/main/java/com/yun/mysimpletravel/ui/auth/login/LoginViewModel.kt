package com.yun.mysimpletravel.ui.auth.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.NAME
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.PROFILE
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.SNS_ID
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.TYPE
import com.yun.mysimpletravel.common.manager.FirebaseManager
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    fun memberCheck(
        userId: String,
        name: String,
        profile: String,
        token: String,
        callBack: (Boolean) -> Unit
    ) {
        FirebaseManager().memberCheck(userId) { isMember ->
            when (isMember) {
                true -> {
                    // 가입된 유저
                    updatePushToken(userId,token, callBack)
                }

                false -> {
                    // 가입되지 않은 유저
                    signUp(userId, name, profile, token, callBack)
                }

                else -> {
                    // error
                    callBack(false)
                }
            }
        }
    }

    private fun updatePushToken(userId: String, token: String, callBack: (Boolean) -> Unit) {
        FirebaseManager().updateToken(userId, token) { isSuccess ->
            callBack(isSuccess)
        }
    }

    private fun signUp(
        userId: String,
        name: String,
        profile: String,
        token: String,
        callBack: (Boolean) -> Unit
    ) {
        FirebaseManager().insertUser(
            userId,
            FirebaseManager().signupParams(name, token, profile)
        ) { isSuccess ->
            callBack(isSuccess)
        }
    }
}