package com.yun.mysimpletravel.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {
    protected val mContext = application.applicationContext

    // 로딩 프로그레스바 변수
    private val _isLoading = MutableLiveData<Boolean>(false)
    open val isLoading: LiveData<Boolean> get() = _isLoading


//    suspend fun <T : Response<R>, R> callApi(api: suspend () -> T, maxRetries: Int = 3): R? {
//        var retries = 0
//        var response: R? = null
//
//        while (retries < maxRetries) {
//            val apiResponse = api()
//            if (apiResponse.isSuccessful) {
////            Log.d("lys","callApi success > ${api.body()}")
//                response = apiResponse.body()
//                break
//            } else {
//                retries++
//            }
//        }
//        return response
//    }
}