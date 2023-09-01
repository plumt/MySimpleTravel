package com.yun.mysimpletravel.ui.home.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.BaseRecyclerAdapter
import com.yun.mysimpletravel.common.constants.NavigationConstants.Type.EXIT
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.APP_VERSION
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.LOG_OUT
import com.yun.mysimpletravel.common.constants.SettingConstants.Settings.SIGN_OUT
import com.yun.mysimpletravel.common.manager.KakaoAuthManager
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.common.manager.SharedPreferenceManager
import com.yun.mysimpletravel.data.model.setting.SettingDataModel
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.databinding.FragmentSettingBinding
import com.yun.mysimpletravel.databinding.ItemSettingBinding
import com.yun.mysimpletravel.util.PreferenceUtil
import com.yun.mysimpletravel.util.Util
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>(),
    KakaoAuthManager.KakaoInterface {
    override val viewModel: SettingViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_setting
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.setting

    private lateinit var kakaoAuthManager: KakaoAuthManager
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var sPrefs: PreferenceUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kakaoAuthManager = KakaoAuthManager(requireActivity(), this)
        sharedPreferenceManager = SharedPreferenceManager(requireActivity(), sPrefs)
        navigationManager = NavigationManager(requireActivity(), view)

        binding.rvSetting.run {
            adapter = object : BaseRecyclerAdapter.Create<SettingDataModel, ItemSettingBinding>(
                layoutResId = R.layout.item_setting,
                bindingVariableId = BR.itemSetting,
                bindingListener = BR.settingListener
            ) {
                override fun onItemClick(item: SettingDataModel, view: View) {
                    when (item.id) {
                        APP_VERSION -> {
                            if (!item.contents.isNullOrEmpty()) {
                                // TODO 스토어 이동
                            }
                        }

                        LOG_OUT -> {
                            kakaoAuthManager.kakaoLogOut()
                        }

                        SIGN_OUT -> {
                            //TODO 회원탈퇴 api 리턴 받은 이후 kakao 회원 탈퇴가 코드 실행
                            kakaoAuthManager.kakaoSignOut()
                        }
                    }
                }

                override fun onItemLongClick(item: SettingDataModel, view: View): Boolean = true
            }
        }


        viewModel.isLoading.observe(viewLifecycleOwner) {
            Log.d("lys", "isLoading > $it")
        }
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
        //TODO 로그인 화면으로 이동
    }
}