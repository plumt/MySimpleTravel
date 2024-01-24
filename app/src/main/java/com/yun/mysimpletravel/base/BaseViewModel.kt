package com.yun.mysimpletravel.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.util.Util.isNetworkConnected
import retrofit2.Response

open class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {
    protected val mContext = application.applicationContext

    // 로딩 프로그레스바 변수
    private val _isLoading = MutableLiveData<Boolean>()
    open val isLoading: LiveData<Boolean> get() = _isLoading


//    suspend fun <T : Response<R>, R> callApi(api: suspend () -> T, maxRetries: Int = 3): R? {
//        if (!isNetworkConnected(mContext)) return null
//        var retries = 0
//        while (retries < maxRetries) {
//            try {
//                val apiResponse = api()
//                if (apiResponse.isSuccessful) return apiResponse.body()
//                else Log.e("lys", "ApiResponse Error $retries")
//            } catch (e: Exception) {
//                Log.e("lys", "Coroutine Exception: $e")
//            }
//            retries++
//        }
//        return null
//    }
}