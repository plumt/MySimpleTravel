package com.yun.mysimpletravel.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.NavigationConstants.Type.ENTER
import com.yun.mysimpletravel.common.manager.KakaoAuthManager
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.common.manager.SharedPreferenceManager
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.databinding.FragmentSplashBinding
import com.yun.mysimpletravel.util.PreferenceUtil
import com.yun.mysimpletravel.util.Util.calculateTimeDifferenceInSeconds
import com.yun.mysimpletravel.util.Util.delayedHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_splash
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {
        // TODO 네트워크 통신 중에는 막지만, 통신 에러로 스플래시에서 멈춘 경우엔 나갈 수 있게 팝업 등을 띄워야 함
    }

    override fun setVariable(): Int = BR.splash

    private lateinit var navigationManager: NavigationManager
    private lateinit var kakaoManager: KakaoAuthManager
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    @Inject
    lateinit var sPrefs: PreferenceUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationManager = NavigationManager(view)
        kakaoManager = KakaoAuthManager(requireActivity(), kakaoInterface)
        sharedPreferenceManager = SharedPreferenceManager(requireActivity(), sPrefs)
    }

    /**
     * 로그인 화면 이동
     */
    private fun moveLoginScreen() {
        try {
            delayedHandler(calculateTimeDifferenceInSeconds(viewModel.startingAppTime)) {
                navigationManager.movingScreen(R.id.action_splashFragment_to_loginFragment, ENTER)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 홈 화면 이동
     */
    private fun moveHomeScreen() {
        try {
            delayedHandler(calculateTimeDifferenceInSeconds(viewModel.startingAppTime)) {
                navigationManager.movingScreen(R.id.action_splashFragment_to_homeFragment, ENTER)
                sharedVM.setBottomNav(true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val kakaoInterface = object : KakaoAuthManager.KakaoAuthInterface {

        override fun kakaoError(t: Throwable) {
            // 카카오 로그인 에러
            moveLoginScreen()
            t.printStackTrace()
        }

        override fun loginUserInfo(info: UserInfoDataModel) {
            // 카카오 로그인 성공
            sharedPreferenceManager.setUserInfo(info)
            moveHomeScreen()
        }

        override fun removeUser() {
            // 카카오 로그아웃 / 회원탈퇴
        }
    }


    override fun onResume() {
        super.onResume()

        //TODO 1순위 권한 체크
        //TODO 2순위 앱 버전 체크

        //TODO 가입 도중에 앱을 종료하거나 뒤로 할 경우, SNS 로그아웃 시켜야함
        viewModel.automaticLoginStatus { isLogin ->
            if (isLogin) {
                // 로그인 정보 있음 > 홈 화면
                kakaoManager.snsTokenCheck()
            } else {
                // 로그인 정보 없음 > 로그인 화면
                moveLoginScreen()
            }
        }
    }
}