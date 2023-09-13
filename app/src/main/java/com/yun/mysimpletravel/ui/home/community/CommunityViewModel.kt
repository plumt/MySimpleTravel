package com.yun.mysimpletravel.ui.home.community

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.base.ListLiveData
import com.yun.mysimpletravel.data.model.community.CommunityDataModel
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.util.Util.delayedHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>(true)
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _communityList = ListLiveData<CommunityDataModel.RS>()
    val communityList: ListLiveData<CommunityDataModel.RS> get() = _communityList

    init {
        delayedHandler(100) {
            setData()
        }
    }

    fun setData(clear: Boolean = false): Boolean {
        setLoading(true)
        if (clear) {
            // 초기화
            _communityList.clear(true)
        }

        val profile =
            "https://k.kakaocdn.net/dn/bog87f/btss3F1JVQw/18XAftomGevqjvJ4h2t09k/img_640x640.jpg"
        val img = "https://t1.daumcdn.net/cfile/tistory/2463694C53D0A5D806"
//        val img = "https://k.kakaocdn.net/dn/bog87f/btss3F1JVQw/18XAftomGevqjvJ4h2t09k/img_640x640.jpg"

        for (i in 0..8) {
            _communityList.add(
                CommunityDataModel.RS(
                    _communityList.sizes(),
                    UserInfoDataModel("test", "testNm", profile, ""),
                    "testContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContents",
                    img,
                    false
                )
            )
        }
        setLoading(false)
        return true
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}