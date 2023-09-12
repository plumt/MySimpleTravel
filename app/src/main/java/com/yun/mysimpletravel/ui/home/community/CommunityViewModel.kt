package com.yun.mysimpletravel.ui.home.community

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.base.ListLiveData
import com.yun.mysimpletravel.data.model.community.CommunityDataModel
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _communityList = ListLiveData<CommunityDataModel.RS>()
    val communityList: ListLiveData<CommunityDataModel.RS> get() = _communityList

    init {
        val profile =
            "https://k.kakaocdn.net/dn/bog87f/btss3F1JVQw/18XAftomGevqjvJ4h2t09k/img_640x640.jpg"
        val img = "https://t1.daumcdn.net/cfile/tistory/2463694C53D0A5D806"
        _communityList.add(
            CommunityDataModel.RS(
                0,
                UserInfoDataModel("test", "testNm", profile, ""),
                "testContents",
                img
            )
        )
        _communityList.add(
            CommunityDataModel.RS(
                1,
                UserInfoDataModel("test", "testNm", profile, ""),
                "testContents",
                img
            )
        )
        _communityList.add(
            CommunityDataModel.RS(
                2,
                UserInfoDataModel("test", "testNm", profile, ""),
                "testContents",
                img
            )
        )
        _communityList.add(
            CommunityDataModel.RS(
                3,
                UserInfoDataModel("test", "testNm", profile, ""),
                "testContents",
                img
            )
        )
        _communityList.add(
            CommunityDataModel.RS(
                4,
                UserInfoDataModel("test", "testNm", profile, ""),
                "testContents",
                img
            )
        )

        _communityList.add(
            CommunityDataModel.RS(
              5,
                UserInfoDataModel("test", "testNm", profile, ""),
                "testContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContents",
                img
            )
        )

        _communityList.add(
            CommunityDataModel.RS(
                6,
                UserInfoDataModel("test", "testNm", profile, ""),
                "testContents",
                img
            )
        )
    }
}