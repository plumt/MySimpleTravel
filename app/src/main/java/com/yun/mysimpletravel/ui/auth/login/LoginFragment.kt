package com.yun.mysimpletravel.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.NavigationConstants
import com.yun.mysimpletravel.common.manager.KakaoAuthManager
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.common.manager.SharedPreferenceManager
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.databinding.FragmentLoginBinding
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(),
    KakaoAuthManager.KakaoInterface {
    override val viewModel: LoginViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_login
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.login

    private lateinit var navigationManager: NavigationManager
    private lateinit var kakaoManager: KakaoAuthManager
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    @Inject
    lateinit var sPrefs: PreferenceUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationManager = NavigationManager(requireActivity(), view)
        kakaoManager = KakaoAuthManager(requireActivity(), this)
        sharedPreferenceManager = SharedPreferenceManager(requireActivity(), sPrefs)

        binding.ivKakaoLoginButton.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view) {
            binding.ivKakaoLoginButton -> {
                kakaoManager.kakaoLogin()
            }
        }
    }

    /**
     * 홈 화면 이동
     */
    private fun moveHomeScreen() {
        navigationManager.movingScreen(
            R.id.action_loginFragment_to_homeFragment,
            NavigationConstants.Type.ENTER
        )
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
    override fun loginUserInfo(info: UserInfoDataModel) {
        sharedPreferenceManager.setUserInfo(info)
        moveHomeScreen()
        Log.d("lys", "loginUserInfo > $info")
    }

    /**
     * 카카오 로그아웃 / 회원탈퇴
     */
    override fun removeUser() {}
}