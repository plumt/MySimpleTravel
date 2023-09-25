package com.yun.mysimpletravel.common.manager

import android.content.Context
import android.util.Log
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Key.NAME
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Key.PROFILE
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Key.PUSH_TOKEN
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Path.USERS
import java.util.Arrays


class FirebaseManager {

    /**
     * fcm 푸쉬 토큰 발급
     */
    fun getToken(callBack: (String?) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener {
                callBack(it)
            }
            .addOnFailureListener {
                it.printStackTrace()
                callBack(null)
            }
    }

    /**
     * fcm 푸쉬 토큰 삭제
     */
    fun deleteToken(callBack: (Boolean) -> Unit) {
        FirebaseMessaging.getInstance().deleteToken()
            .addOnCompleteListener { callBack(it.isSuccessful) }
    }

    /**
     * 푸쉬 토큰 업데이트
     */
    fun updateToken(userId: String, token: String, callBack: (Boolean) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection(USERS)
            .document(userId)
            .update(PUSH_TOKEN, token)
            .addOnCompleteListener { callBack(it.isSuccessful) }
    }

    /**
     * fcm send 하기 위한 access token 발급 함수
     */
    fun getAccessToken(context: Context): String {
        val inputStream = context.assets.open("service-account.json")
        val googleCredentials = GoogleCredentials
            .fromStream(inputStream)
            .createScoped(
                Arrays.asList<String>("https://www.googleapis.com/auth/firebase.messaging")
            )
        googleCredentials.refresh()
        Log.d(
            "lys",
            "googleCredentials.accessToken.tokenValue > ${googleCredentials.accessToken.tokenValue}"
        )

        inputStream.close()
        return googleCredentials.accessToken.tokenValue
    }

    /**
     * 가입된 유저인지 체크
     */
    fun memberCheck(userId: String, callBack: (Boolean?) -> Unit) {
        FirebaseFirestore.getInstance().collection(USERS).document(userId).get()
            .addOnSuccessListener { document ->
                callBack(document != null && document.exists())
            }
            .addOnFailureListener { callBack(null) }
    }

    /**
     * 유저 등록
     */
    fun insertUser(userId: String, info: Map<String, Any>, callBack: (Boolean) -> Unit) {
        FirebaseFirestore.getInstance().collection(USERS)
            .document(userId)
            .set(info)
            .addOnCompleteListener { callBack(it.isSuccessful) }
    }

    /**
     * 유저 삭제
     */
    fun deleteUser(userId: String, callBack: (Boolean) -> Unit) {
        deleteToken{isSuccess ->
            if (isSuccess) {
                FirebaseFirestore.getInstance()
                    .collection(USERS)
                    .document(userId)
                    .delete().addOnCompleteListener { callBack(it.isSuccessful) }
            } else callBack(false)
        }
    }

    fun signupParams(nickName: String, token: String, profile: String) = mapOf(
        NAME to nickName,
        PUSH_TOKEN to token,
        PROFILE to profile
    )
}