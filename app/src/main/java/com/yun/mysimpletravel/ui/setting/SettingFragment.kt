package com.yun.mysimpletravel.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.MainActivity
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.BaseRecyclerAdapter
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.NAME
import com.yun.mysimpletravel.common.constants.LocationConstants.LocationCode.JEJU
import com.yun.mysimpletravel.common.constants.LocationConstants.LocationCode.SEOGWIP
import com.yun.mysimpletravel.common.constants.LocationConstants.SearchCode.JEJU_ALL
import com.yun.mysimpletravel.common.constants.LocationConstants.SearchCode.JEJU_JEJU
import com.yun.mysimpletravel.common.constants.LocationConstants.SearchCode.JEJU_SEOGWIP
import com.yun.mysimpletravel.common.constants.NavigationConstants.Type.EXIT
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.APP_VERSION
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.LOCATION_CHANGED
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.LOG_OUT
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.SIGN_OUT
import com.yun.mysimpletravel.common.manager.KakaoAuthManager
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.common.manager.SharedPreferenceManager
import com.yun.mysimpletravel.data.model.setting.SettingDataModel
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.databinding.FragmentSettingBinding
import com.yun.mysimpletravel.databinding.ItemSettingBinding
import com.yun.mysimpletravel.data.model.location.LocationModel
import com.yun.mysimpletravel.data.repository.location.LocationRepository
import com.yun.mysimpletravel.data.repository.location.LocationRepositoryImpl
import com.yun.mysimpletravel.ui.bottomsheet.location.LocationBottomSheet
import com.yun.mysimpletravel.ui.popup.ButtonPopup
import com.yun.mysimpletravel.util.FirebaseUtil
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {
    override val viewModel: SettingViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_setting
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.setting

    private lateinit var kakaoAuthManager: KakaoAuthManager
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var navigationManager: NavigationManager
    private lateinit var buttonPopup: ButtonPopup

    private lateinit var locationBottomSheet: LocationBottomSheet<LocationModel>

    @Inject
    lateinit var sPrefs: PreferenceUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kakaoAuthManager = KakaoAuthManager(requireActivity(), kakaoInterface)
        sharedPreferenceManager = SharedPreferenceManager(requireActivity(), sPrefs)
        navigationManager = NavigationManager(view)
        locationBottomSheet = LocationBottomSheet(locationBottomSheetInterface)
        buttonPopup = ButtonPopup(requireActivity())

        init()

        binding.rvSetting.run {
//            setHasFixedSize(true)
            adapter = object : BaseRecyclerAdapter.Create<SettingDataModel, ItemSettingBinding>(
                layoutResId = R.layout.item_setting,
                bindingVariableId = BR.itemSetting,
                bindingListener = BR.settingListener
            ) {
                override fun onItemClick(item: SettingDataModel, view: View) {
                    when (item.id) {
                        LOCATION_CHANGED -> {
//                            lifecycleScope.launch {
                            locationBottomSheet.setLoading(true)
                            locationBottomSheet.show(
                                requireActivity().supportFragmentManager,
                                locationBottomSheet.tag
                            )
                            searchLocationCode(JEJU_ALL)
//                            }
                        }

                        APP_VERSION -> {
                            if (!item.contents.isNullOrEmpty()) {
                                // TODO 스토어 이동
                            }
                            Log.d("lys", "click!")
                        }

                        LOG_OUT -> {
                            buttonPopup.showPopup(requireActivity().getString(R.string.tv_btn_popup_notice_title),
                                requireActivity().getString(R.string.tv_btn_popup_log_out_message),
                                requireActivity().getString(R.string.btn_btn_popup_log_out_nm),
                                requireActivity().getString(R.string.btn_btn_popup_cancel_nm),
                                object : ButtonPopup.ButtonDialogListener {
                                    override fun onButtonClick(result: Boolean) {
                                        if (result) {
                                            FirebaseUtil.logout { isSuccess ->
                                                if (isSuccess) kakaoAuthManager.kakaoLogOut()
                                            }
                                        }
                                    }
                                })
                        }

                        SIGN_OUT -> {
                            //TODO 회원탈퇴 api 리턴 받은 이후 kakao 회원 탈퇴가 코드 실행
                            buttonPopup.showPopup(requireActivity().getString(R.string.tv_btn_popup_notice_title),
                                requireActivity().getString(R.string.tv_btn_popup_sign_out_message),
                                requireActivity().getString(R.string.btn_btn_popup_sign_out_nm),
                                requireActivity().getString(R.string.btn_btn_popup_cancel_nm),
                                object : ButtonPopup.ButtonDialogListener {
                                    override fun onButtonClick(result: Boolean) {
                                        if (result) {
                                            FirebaseUtil.signOut { isSuccess ->
                                                if (isSuccess) kakaoAuthManager.kakaoSignOut()
                                            }
                                        }
                                    }
                                })
                        }
                    }
                }

                override fun onItemLongClick(item: SettingDataModel, view: View): Boolean = true
            }
        }
    }

    private fun init() {
        binding.tvUserName.text = sPrefs.getString(requireActivity(), NAME) ?: ""
    }

    /**
     * 로그인 화면 이동
     */
    private fun moveLoginScreen() {
//        sharedVM.setBottomNav(false)
//        (activity as MainActivity).bottomButtonSelectedClear()
//        navigationManager.movingScreen(R.id.action_settingFragment_to_loginFragment, EXIT)
    }

    private val kakaoInterface = object : KakaoAuthManager.KakaoInterface {
        override fun kakaoError(t: Throwable) {
            // 카카오 로그인 에러
            t.printStackTrace()
        }

        override fun loginUserInfo(info: UserInfoDataModel) {
            // 카카오 로그인 성공
        }

        override fun removeUser() {
            // 카카오 로그아웃 / 회원탈퇴
            sharedPreferenceManager.removeUserInfo()
            moveLoginScreen()
        }
    }

    private val locationBottomSheetInterface =
        object : LocationBottomSheet.LocationBottomSheetInterface<LocationModel> {
            override fun onLocationItemClick(item: LocationModel) {
                // location bottom sheet item click
                locationBottomSheet.setLoading(true)
                when (item.code) {
                    JEJU -> searchLocationCode(JEJU_JEJU)
                    SEOGWIP -> searchLocationCode(JEJU_SEOGWIP)
                    else -> {
                        sharedPreferenceManager.updateLocationName(item.name)
                        viewModel.updateSelLocData(item.name)
                        locationBottomSheet.dismiss()

                    }
                }
            }
        }

    private fun searchLocationCode(code: String) {
        lifecycleScope.launch {
            viewModel.searchLocCode(code,
                object : LocationRepositoryImpl.GetDataCallBack<List<LocationModel>> {
                    override fun onSuccess(data: List<LocationModel>) {
                        changeLocation(data)
                    }

                    override fun onFailure(throwable: Throwable) {
                        throwable.printStackTrace()
                    }
                }
            )
        }
    }
    private fun changeLocation(param: List<LocationModel>) {
        locationBottomSheet.setLocationList(param)
        locationBottomSheet.setLoading(false)
    }
}