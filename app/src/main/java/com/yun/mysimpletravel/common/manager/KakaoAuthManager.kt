package com.yun.mysimpletravel.common.manager

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.common.constants.AuthConstants.Type.KAKAO
import com.yun.mysimpletravel.common.constants.ErrorConstants.Message.NETWORK_NOT_CONNECT
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.util.Util.isNetworkConnected
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Base64

class KakaoAuthManager(private val context: Context, private val kakaoInterface: KakaoInterface) {

    interface KakaoInterface {
        fun kakaoError(t: Throwable)
        fun loginUserInfo(info: UserInfoDataModel)
        fun removeUser()
    }

    init {
        getHashKey()
        kakaoSdkSetting()
    }

    /**
     * kakao 토큰 검사
     */
    fun snsTokenCheck() {
        if (!isNetworkConnected(context)) {
            kakaoInterface.kakaoError(customException(NETWORK_NOT_CONNECT))
            return
        }
        UserApiClient.instance.me { _, error ->
            if (error != null) kakaoInterface.kakaoError(error)
            else kakaoUserInfo()
        }
    }

    /**
     * 카카오 로그인
     */
    fun kakaoLogin() {
        if (!isNetworkConnected(context)) {
            kakaoInterface.kakaoError(customException(NETWORK_NOT_CONNECT))
            return
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                error?.let {
                    kakaoInterface.kakaoError(it)
                    if (it is ClientError && it.reason == ClientErrorCause.Cancelled) {
                        // 카카오톡에서 취소한 경우, 의도적 취소로 판단하여 로그인 취소 처리
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(
                        context,
                        callback = kakaoLoginCallback
                    )
                }
                token?.let { kakaoUserInfo() }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                context,
                callback = kakaoLoginCallback
            )
        }
    }

    /**
     * 카카오 로그아웃
     */
    fun kakaoLogOut() {
        UserApiClient.instance.logout { error ->
            if (error != null) kakaoInterface.kakaoError(error)
            else kakaoInterface.removeUser()
        }
    }

    /**
     * 카카오 회원탈퇴
     */
    fun kakaoSignOut() {
        UserApiClient.instance.unlink { error ->
            if (error != null) kakaoInterface.kakaoError(error)
            else kakaoInterface.removeUser()
        }
    }

    /**
     * 커스텀 오류 메시지 출력
     */
    private fun customException(message: String) = Throwable(message)

    /**
     * kakao sns login sdk setting
     */
    private fun kakaoSdkSetting() {
        if (!isNetworkConnected(context)) {
            kakaoInterface.kakaoError(customException(NETWORK_NOT_CONNECT))
            return
        }
        KakaoSdk.init(context, context.getString(R.string.social_login_info_kakao_native_key))
    }

    /**
     * 로그인 성공시 유저 정보 출력
     */
    private fun kakaoUserInfo() {
        UserApiClient.instance.me { user, error ->
            error?.let { kakaoInterface.kakaoError(it) }
            user?.let {
                Log.d("lys", "kakao login success > $it")
                val userInfo = UserInfoDataModel(
                    userId = it.id.toString(),
                    userEmail = it.kakaoAccount!!.email!!,
                    userName = it.properties?.get("nickname") ?: "여행자",
                    userProfileUrl = it.properties?.get("profile_image") ?: "",
                    loginType = KAKAO,
                    ""
                )
                kakaoInterface.loginUserInfo(userInfo)
            }
        }
    }

    /**
     * 카카오 로그인 이벤트
     */
    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, throwable ->
        throwable?.let { kakaoInterface.kakaoError(it) }
        token?.let { kakaoUserInfo() }
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                //Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                Log.d("KeyHash", Base64.getEncoder().encodeToString(md.digest()))
            } catch (e: NoSuchAlgorithmException) {
                Log.d("KeyHash", "Unable to get MessageDigest. signature=$signature")

            }
        }
    }
}