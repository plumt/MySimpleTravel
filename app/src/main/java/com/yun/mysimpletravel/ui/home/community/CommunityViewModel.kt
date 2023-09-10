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
        _communityList.add(CommunityDataModel.RS(
            0, UserInfoDataModel("test","testNm","",""),"testTitle","testContents", arrayListOf()
        ))
    }
}