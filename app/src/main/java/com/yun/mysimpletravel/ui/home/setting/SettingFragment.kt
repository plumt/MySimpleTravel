package com.yun.mysimpletravel.ui.home.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.BaseRecyclerAdapter
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.SETTING
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
import com.yun.mysimpletravel.data.model.location.LocationDataModel
import com.yun.mysimpletravel.data.model.setting.SettingDataModel
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.databinding.FragmentSettingBinding
import com.yun.mysimpletravel.databinding.ItemSettingBinding
import com.yun.mysimpletravel.ui.bottomsheet.location.LocationBottomSheet
import com.yun.mysimpletravel.ui.home.HomeViewModel
import com.yun.mysimpletravel.ui.home.ViewPagerCallback
import com.yun.mysimpletravel.ui.popup.ButtonPopup
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>(),
    KakaoAuthManager.KakaoInterface, ViewPagerCallback,
    LocationBottomSheet.LocationBottomSheetInterface<LocationDataModel.Items> {
    override val viewModel: SettingViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_setting
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.setting

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    private lateinit var kakaoAuthManager: KakaoAuthManager
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var navigationManager: NavigationManager
    private lateinit var buttonPopup: ButtonPopup

    private lateinit var locationBottomSheet: LocationBottomSheet<LocationDataModel.Items>

    @Inject
    lateinit var sPrefs: PreferenceUtil

    override fun onPageSelected(position: Int) {
        if (position == SETTING) {
            visibilityParentLayout(View.VISIBLE)
        } else {
            visibilityParentLayout(View.INVISIBLE)
        }
    }

    override fun onReselected() {
    }

    private fun visibilityParentLayout(visibility: Int) {
        if (isBindingInitialized) binding.layoutParent.visibility = visibility
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kakaoAuthManager = KakaoAuthManager(requireActivity(), this)
        sharedPreferenceManager = SharedPreferenceManager(requireActivity(), sPrefs)
        navigationManager = NavigationManager(requireActivity(), view)
        locationBottomSheet = LocationBottomSheet(this)
        buttonPopup = ButtonPopup(requireActivity())

        binding.rvSetting.run {
            setHasFixedSize(true)
            adapter = object : BaseRecyclerAdapter.Create<SettingDataModel, ItemSettingBinding>(
                layoutResId = R.layout.item_setting,
                bindingVariableId = BR.itemSetting,
                bindingListener = BR.settingListener
            ) {
                override fun onItemClick(item: SettingDataModel, view: View) {
                    when (item.id) {

                        LOCATION_CHANGED -> {
                            lifecycleScope.launch {
                                locationBottomSheet.setLoading(true)
                                locationBottomSheet.show(
                                    requireActivity().supportFragmentManager,
                                    locationBottomSheet.tag
                                )
                                viewModel.searchLocCode(JEJU_ALL)?.regcodes?.let {
                                    changeLocation(it)
                                }
                            }
                        }

                        APP_VERSION -> {
                            if (!item.contents.isNullOrEmpty()) {
                                // TODO 스토어 이동
                            }
                        }

                        LOG_OUT -> {
                            buttonPopup.showPopup(requireActivity().getString(R.string.tv_btn_popup_notice_title),
                                requireActivity().getString(R.string.tv_btn_popup_log_out_message),
                                requireActivity().getString(R.string.btn_btn_popup_log_out_nm),
                                requireActivity().getString(R.string.btn_btn_popup_cancel_nm),
                                object : ButtonPopup.ButtonDialogListener {
                                    override fun onButtonClick(result: Boolean) {
                                        if (result) kakaoAuthManager.kakaoLogOut()
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
                                        if (result) kakaoAuthManager.kakaoSignOut()
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

    }

    /**
     * 로그인 화면 이동
     */
    private fun moveLoginScreen() {
        navigationManager.movingScreen(R.id.action_homeFragment_to_loginFragment, EXIT)
    }

    /**
     * 카카오 로그인 에러
     */
    override fun kakaoError(t: Throwable) {
        t.printStackTrace()
    }

    /**
     * 카카로 로그인 성공
     */
    override fun loginUserInfo(info: UserInfoDataModel) {}

    /**
     * 카카오 로그아웃 / 회원탈퇴
     */
    override fun removeUser() {
        sharedPreferenceManager.removeUserInfo()
        moveLoginScreen()
    }

    /**
     * location bottom sheet item click
     */
    override fun onLocationItemClick(item: LocationDataModel.Items) {
        lifecycleScope.launch {
            locationBottomSheet.setLoading(true)
            when (item.code) {
                JEJU -> viewModel.searchLocCode(JEJU_JEJU)
                SEOGWIP -> viewModel.searchLocCode(JEJU_SEOGWIP)
                else -> {
                    Log.d("lys", "select > ${item.code}")
                    sharedPreferenceManager.updateLocation(
                        name = item.name,
                        fullName = item.fullName
                    )
                    viewModel.updateSelLocData(item.fullName)
                    locationBottomSheet.dismiss()
                    null
                }
            }?.regcodes?.let { changeLocation(it) }
        }
    }

    private fun changeLocation(param: ArrayList<LocationDataModel.Items>) {
        locationBottomSheet.setLocationList(param)
        locationBottomSheet.setLoading(false)
    }
}