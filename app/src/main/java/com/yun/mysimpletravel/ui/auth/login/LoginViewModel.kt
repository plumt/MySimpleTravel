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

    /**
     * 가입된 유저인지 체크
     * 가입되어 있으면 푸쉬 토큰 업데이트 이후 홈 화면 이동
     * 가입되어 있지 않으면 유저 정보를 등록 후 홈 화면 이동
     */
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
                    // 가입 되지 않은 유저
                    signUp(userId, name, profile, token, callBack)
                }

                else -> {
                    // error
                    callBack(false)
                }
            }
        }
    }

    /**
     * 푸쉬 토큰값을 Firestore에 업데이트
     */
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