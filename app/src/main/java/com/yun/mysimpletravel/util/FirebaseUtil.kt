package com.yun.mysimpletravel.util

import android.content.Context
import android.util.Log
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.yun.mysimpletravel.common.constants.FirebaseConstants
import java.util.Arrays

object FirebaseUtil {
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
     * firestore 유저 정보 업데이트
     */
    fun updateUserInfo(
        userId: String,
        name: String,
        profile: String,
        token: String,
        callBack: (Boolean) -> Unit
    ) {
        FirebaseFirestore.getInstance()
            .collection(FirebaseConstants.Path.USERS)
            .document(userId)
            .update(
                mapOf(
                    FirebaseConstants.Key.PUSH_TOKEN to token,
                    FirebaseConstants.Key.NAME to name,
                    FirebaseConstants.Key.PROFILE to profile
                )
            )
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
     * firestore 가입된 유저인지 체크
     */
    fun memberCheck(userId: String, callBack: (Boolean?) -> Unit) {
        FirebaseFirestore.getInstance().collection(FirebaseConstants.Path.USERS).document(userId)
            .get()
            .addOnSuccessListener { document ->
                callBack(document != null && document.exists())
            }
            .addOnFailureListener { callBack(null) }
    }


    /**
     * firestroe 유저 등록
     */
    fun insertUser(userId: String, info: Map<String, Any>, callBack: (Boolean) -> Unit) {
        FirebaseFirestore.getInstance().collection(FirebaseConstants.Path.USERS)
            .document(userId)
            .set(info)
            .addOnCompleteListener { callBack(it.isSuccessful) }
    }

    /**
     * firestore 유저 삭제
     */
    fun deleteUser(userId: String, callBack: (Boolean) -> Unit) {
        deleteToken { isSuccess ->
            if (isSuccess) {
                FirebaseFirestore.getInstance()
                    .collection(FirebaseConstants.Path.USERS)
                    .document(userId)
                    .delete().addOnCompleteListener { callBack(it.isSuccessful) }
            } else callBack(false)
        }
    }

    /**
     *  firestore 회원가입 파라미터
     */
    fun signupParams(nickName: String, token: String, profile: String) = mapOf(
        FirebaseConstants.Key.NAME to nickName,
        FirebaseConstants.Key.PUSH_TOKEN to token,
        FirebaseConstants.Key.PROFILE to profile
    )

    /**
     * firebase auth 로그인 여부 체크
     */
    fun userInfo() = FirebaseAuth.getInstance().currentUser

    fun checkUser(callBack: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().currentUser?.getIdToken(true)
            ?.addOnCompleteListener { task ->
                callBack(task.isSuccessful)
            } ?: callBack(false)
    }

    /**
     * firebase auth 회원 가입
     */
    fun signUp(email: String, password: String, callBack: (Boolean) -> Unit) {
        // A network error
        // The email address is already in use by another account
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callBack(task.isSuccessful)
            }
    }

    fun login(email: String, password: String, callBack: (Boolean) -> Unit) {
        // The user account has been disabled by an administrator.
        // An internal error has occurred. [ INVALID_LOGIN_CREDENTIALS ]
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) signUp(email, password, callBack)
                else callBack(true)
            }
    }

    fun logout(callBack: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().signOut()
        callBack(true)
    }

    fun signOut(callBack: (Boolean) -> Unit) {
        if (FirebaseAuth.getInstance().currentUser == null) {
            callBack(false)
            return
        }
        FirebaseAuth.getInstance().currentUser!!.delete()
            .addOnCompleteListener { task ->
                callBack(task.isSuccessful)
            }
    }

}