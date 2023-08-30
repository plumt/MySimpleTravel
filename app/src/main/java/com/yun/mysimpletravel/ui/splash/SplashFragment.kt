package com.yun.mysimpletravel.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.NavigationConstants
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_splash
    override fun isLoading(): LiveData<Boolean> = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {
        // TODO 네트워크 통신 중에는 막지만, 통신 에러로 스플래시에서 멈춘 경우엔 나갈 수 있게 팝업 등을 띄워야 함
    }

    override fun setVariable(): Int = BR.splash

    lateinit var navigationManager: NavigationManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationManager = NavigationManager(requireActivity(), view)

        //TODO 1순위 권한 체크
        //TODO 2순위 앱 버전 체크
        //TODO 3순위 로그인 여부 체크

        //TODO 가입 도중에 앱을 종료하거나 뒤로 할 경우, SNS 로그아웃 시켜야함

        if (viewModel.automaticLoginStatus()) {
            // 로그인 정보 있음 > 홈 화면
        } else {
            // 로그인 정보 없음 > 로그인 화면
            navigationManager.movingScreen(
                R.id.action_splashFragment_to_loginFragment,
                NavigationConstants.Type.ENTER
            )
        }
    }
}