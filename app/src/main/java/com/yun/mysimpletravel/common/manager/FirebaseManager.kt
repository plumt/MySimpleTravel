package com.yun.mysimpletravel.common.manager

import android.content.Context
import android.util.Log
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.messaging.FirebaseMessaging
import java.util.Arrays


class FirebaseManager(private val context: Context) {

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
     * fcm send 하기 위한 access token 발급 함수
     */
    fun getAccessToken(context: Context) : String {
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
}