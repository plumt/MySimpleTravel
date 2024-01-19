package com.yun.mysimpletravel.util

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.messaging.FirebaseMessaging
import com.yun.mysimpletravel.common.constants.FirebaseConstants
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Community.IMG
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Community.LIKES
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Community.MESSAGE
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Community.TIMESTAMP
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Community.WRITER
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Path.BOARDS
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
                    FirebaseConstants.Auth.PUSH_TOKEN to token,
                    FirebaseConstants.Auth.NAME to name,
                    FirebaseConstants.Auth.PROFILE to profile
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
        FirebaseConstants.Auth.NAME to nickName,
        FirebaseConstants.Auth.PUSH_TOKEN to token,
        FirebaseConstants.Auth.PROFILE to profile
    )

    /**
     * firestore 게시글 가져오기
     */
    fun selectCommunity(lastItem: DocumentSnapshot?, callBack: (Task<QuerySnapshot>) -> Unit) {
        FirebaseFirestore.getInstance().collection(BOARDS)
            .orderBy(TIMESTAMP, Query.Direction.DESCENDING)
            .let { if (lastItem != null) it.startAfter(lastItem) else it }
            .limit(10)
            .get()
            .addOnCompleteListener { task ->
                Log.d("lys","task >>> " + task.result)
                callBack(task)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun communityLikeUpdate(
        writer: String,
        timestamp: Timestamp,
        likes: ArrayList<String>
    ) {
        FirebaseFirestore.getInstance().collection(BOARDS)
            .whereEqualTo(WRITER, writer)
            .whereEqualTo(TIMESTAMP, timestamp)
            .limit(1)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.forEach { snap ->
                        FirebaseFirestore.getInstance().collection(BOARDS)
                            .document(snap.id)
                            .update(mapOf(LIKES to likes))
                            .addOnCompleteListener {}
                    }
                }
            }
    }

    /**
     * 게시글 작성
     */
    fun writeCommunity(userId: String, message: String, imgUrl: String,  callBack: (Boolean) -> Unit) {
        val msg = mapOf(
            IMG to imgUrl,
            LIKES to arrayListOf<String>(),
            MESSAGE to message,
            TIMESTAMP to FieldValue.serverTimestamp(),
            WRITER to userId
        )
        FirebaseFirestore.getInstance().collection(BOARDS)
            .document()
            .set(msg)
            .addOnCompleteListener { callBack(it.isSuccessful) }
    }

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