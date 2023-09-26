package com.yun.mysimpletravel.ui.setting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.api.ApiRepository
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.base.ListLiveData
import com.yun.mysimpletravel.common.constants.ApiConstants.ApiType.LOCATION
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.EMAIL
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.NAME
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.PROFILE
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.PUSH_TOKEN
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
import com.yun.mysimpletravel.common.manager.SharedPreferenceManager
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
        _userInfo.value = SharedPreferenceManager(mContext, sPrefs).userInfo()
    }

    /**
     * 설정 리스트
     */
    private fun setSettingList(locationName: String = "") {
        mContext.getString(R.string.tv_setting_location_title)
        _settingList.clear(true)
        addSettings(
            LOCATION_CHANGED + TITLE,
            mContext.getString(R.string.tv_setting_location_title),
            TITLE
        )
        addSettings(
            LOCATION_CHANGED,
            locationName.ifEmpty { locNmChk() },
            content = mContext.getString(R.string.tv_setting_location_changed)
        )
        addSettings(
            APP_VERSION + TITLE,
            mContext.getString(R.string.tv_setting_device_title),
            TITLE
        )
        addSettings(
            APP_VERSION,
            "${mContext.getString(R.string.tv_setting_app_version)} ${BuildConfig.VERSION_NAME}",
            content = mContext.getString(R.string.tv_setting_app_update)
        )
        addSettings(LOG_OUT + TITLE, mContext.getString(R.string.tv_setting_auth_title), TITLE)
        addSettings(LOG_OUT, mContext.getString(R.string.tv_setting_log_out))
        addSettings(SIGN_OUT, mContext.getString(R.string.tv_setting_sign_out))
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
        return if (name.isNullOrEmpty()) mContext.getString(R.string.tv_setting_no_select_location)
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