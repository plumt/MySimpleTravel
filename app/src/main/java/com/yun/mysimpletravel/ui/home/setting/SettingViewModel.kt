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
import com.yun.mysimpletravel.common.constants.LocationConstants.SearchCode.JEJU_ALL
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

//    private val _locationList = ListLiveData<LocationDataModel.Items>()
//    val locationList: ListLiveData<LocationDataModel.Items> get() = _locationList

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

    private fun setSettingList(locationName: String = "") {
        _settingList.clear(true)
        val locationChanged = arrayListOf(
            SettingDataModel(LOCATION_CHANGED + TITLE, TITLE, "*위치", ""),
            SettingDataModel(
                LOCATION_CHANGED,
                CONTENT,
                locationName.ifEmpty { locationNameCheck() },
                "*변경"
            )
        )
        val appVersion = arrayListOf(
            SettingDataModel(APP_VERSION + TITLE, TITLE,"*기기",""),
            SettingDataModel(
                APP_VERSION,
                CONTENT,
                "*앱 버전 ${BuildConfig.VERSION_NAME}",
                "*업데이트"
            )
        )
        val auth = arrayListOf(
            SettingDataModel(LOG_OUT + TITLE, TITLE, "*계정",""),
            SettingDataModel(
                LOG_OUT,
                CONTENT,
                "*로그아웃",
                ""
            ),
            SettingDataModel(
                SIGN_OUT,
                CONTENT,
                "*회원탈퇴",
                ""
            )
        )


        _settingList.addAll(locationChanged)
        _settingList.addAll(appVersion)
        _settingList.addAll(auth)
//        _settingList.add(locationChanged)
//        _settingList.add(appVersion)
//        _settingList.add(logOut)
//        _settingList.add(signOut)
    }

    suspend fun searchLocationCode(code: String): LocationDataModel.RS? {
        setLoading(loading = true)
        val response = callApi({ locationApi.searchLocationCode(code) })
        response?.regcodes?.forEachIndexed { index, items ->
            items.id = index
            items.name = locationNameFilter(items.name, code)
            items.fullName = locationNameFilter(items.name, JEJU_JEJU)
        }
        setLoading(loading = false)
        return response
    }


    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun updateSelectLocationData(locationName: String) {
        setSettingList(locationName)
    }

    private fun locationNameCheck(): String {
        val name = sPrefs.getString(mContext, LocationConstants.Key.NAME)
        return if (name.isNullOrEmpty()) "*지역을 설정해 주세요"
        else name
    }

    fun locationNameFilter(name: String, code: String): String {
        val result = name.replace(JEJU_PROVINCE, "")
        return when (code) {
            JEJU_JEJU -> result.replace(JEJU, "")
            JEJU_SEOGWIP -> result.replace(SEOGWIP, "")
            else -> result
        }.trim()
    }
}