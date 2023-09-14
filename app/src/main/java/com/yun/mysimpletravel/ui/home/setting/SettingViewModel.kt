package com.yun.mysimpletravel.ui.home.setting

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.api.ApiRepository
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.base.ListLiveData
import com.yun.mysimpletravel.common.constants.ApiConstants.ApiType.LOCATION
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.NAME
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.PROFILE
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.SNS_ID
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.TYPE
import com.yun.mysimpletravel.common.constants.LocationConstants
import com.yun.mysimpletravel.common.constants.LocationConstants.FilterName.JEJU
import com.yun.mysimpletravel.common.constants.LocationConstants.FilterName.JEJU_PROVINCE
import com.yun.mysimpletravel.common.constants.LocationConstants.FilterName.SEOGWIP
import com.yun.mysimpletravel.common.constants.LocationConstants.SearchCode.JEJU_JEJU
import com.yun.mysimpletravel.common.constants.LocationConstants.SearchCode.JEJU_SEOGWIP
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.APP_VERSION
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.LOCATION_CHANGED
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.LOG_OUT
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.SIGN_OUT
import com.yun.mysimpletravel.common.constants.SettingConstants.ViewType.CONTENT
import com.yun.mysimpletravel.common.constants.SettingConstants.ViewType.TITLE
import com.yun.mysimpletravel.data.model.location.LocationDataModel
import com.yun.mysimpletravel.data.model.setting.SettingDataModel
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SettingViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil,
    @Named(LOCATION) private val locationApi: ApiRepository
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _userInfo = MutableLiveData<UserInfoDataModel>()
    val userInfo: LiveData<UserInfoDataModel> get() = _userInfo

    private val _settingList = ListLiveData<SettingDataModel>()
    val settingList: ListLiveData<SettingDataModel> get() = _settingList

    init {
        setUserInfo()
        setSettingList()
        //TODO 서버 앱 최신 버전을 가져와야 함
    }

    /**
     * 유저 정보 불러오기
     */
    private fun setUserInfo() {
        _userInfo.value = UserInfoDataModel(
            userId = sPrefs.getString(mContext, SNS_ID)!!,
            userName = sPrefs.getString(mContext, NAME)!!,
            userProfileUrl = sPrefs.getString(mContext, PROFILE),
            loginType = sPrefs.getString(mContext, TYPE)!!
        )
    }

    /**
     * 설정 리스트
     */
    private fun setSettingList(locationName: String = "") {
        _settingList.clear(true)
        addSettings(LOCATION_CHANGED + TITLE, "*위치", TITLE)
        addSettings(LOCATION_CHANGED, locationName.ifEmpty { locNmChk() }, content = "*변경")
        addSettings(APP_VERSION + TITLE, "*기기", TITLE)
        addSettings(APP_VERSION, "*앱 버전 ${BuildConfig.VERSION_NAME}", content = "*업데이트")
        addSettings(LOG_OUT + TITLE, "*계정", TITLE)
        addSettings(LOG_OUT, "*로그아웃")
        addSettings(SIGN_OUT, "*회원탈퇴")
    }

    private fun addSettings(id: Int, title: String, viewType: Int = CONTENT, content: String = "") {
        _settingList.add(SettingDataModel(id, viewType, title, content))
    }

    suspend fun searchLocCode(code: String): LocationDataModel.RS? {
        val response = callApi({ locationApi.searchLocationCode(code) })
        response?.regcodes?.forEachIndexed { index, items ->
            items.id = index
            items.fullName = locNmFilter(items.name).trim()
            items.name = locNmFilter(items.name, code).trim()
        }
        return response
    }


    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun updateSelLocData(locationName: String) {
        setSettingList(locationName)
    }

    private fun locNmChk(): String {
        val name = sPrefs.getString(mContext, LocationConstants.Key.NAME)
        return if (name.isNullOrEmpty()) "*지역을 설정해 주세요"
        else name
    }

    private fun locNmFilter(name: String, code: String): String {
        val result = name.replace(JEJU_PROVINCE, "")
        return when (code) {
            JEJU_JEJU -> result.replace(JEJU, "")
            JEJU_SEOGWIP -> result.replace(SEOGWIP, "")
            else -> result
        }.trim()
    }

    private fun locNmFilter(name: String): String = name.replace(JEJU_PROVINCE, "")
}