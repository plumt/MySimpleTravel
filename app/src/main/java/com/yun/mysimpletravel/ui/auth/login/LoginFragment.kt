package com.yun.mysimpletravel.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.OnSingleClickListener
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.PUSH_TOKEN
import com.yun.mysimpletravel.common.constants.NavigationConstants
import com.yun.mysimpletravel.common.manager.KakaoAuthManager
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.common.manager.SharedPreferenceManager
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.databinding.FragmentLoginBinding
import com.yun.mysimpletravel.util.FirebaseUtil
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
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

        navigationManager = NavigationManager(view)
        kakaoManager = KakaoAuthManager(requireActivity(), kakaoInterface)
        sharedPreferenceManager = SharedPreferenceManager(requireActivity(), sPrefs)

//        FirebaseManager().getToken { pushToken ->
//            Log.d("lys", "push token > $pushToken")
//            sPrefs.setString(requireActivity(), PUSH_TOKEN, pushToken ?: "")
//        }


//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                val accessToken = firebaseManager.getAccessToken(requireActivity())
//                Log.d("lys", "access token > $accessToken")
//            }
//        }

        FirebaseUtil.getToken { pushToken ->
            Log.d("lys", "push token > $pushToken")
            sPrefs.setString(requireActivity(), PUSH_TOKEN, pushToken ?: "")
        }

        binding.ivKakaoLoginButton.setOnClickListener(onSingleClickListener)
    }

    private val onSingleClickListener = object : OnSingleClickListener() {
        override fun onSingleClick(v: View) {
            when (v) {
                binding.ivKakaoLoginButton -> {
                    kakaoManager.kakaoLogin()
                }
            }
        }
    }

    /**
     * 홈 화면 이동
     */
    private fun moveHomeScreen() {
        sharedVM.setBottomNav(true)
        navigationManager.movingScreen(
            R.id.action_loginFragment_to_homeFragment,
            NavigationConstants.Type.ENTER
        )
    }

    private val kakaoInterface = object : KakaoAuthManager.KakaoInterface {
        override fun kakaoError(t: Throwable) {
            // 카카오 로그인 에러
            t.printStackTrace()
            Log.e("lys", "login error > ${t.message}")
        }

        override fun loginUserInfo(info: UserInfoDataModel) {
            // 카카오 로그인 성공
            viewModel.login(info.userId, info.userEmail) { isSuccess ->
                if (isSuccess) {
                    // 로그인 or 회원가입 성공
                    sharedPreferenceManager.setUserInfo(info)
                    moveHomeScreen()
                } else {
                    // 로그인 or 회원가입 실패
                    kakaoManager.kakaoLogOut()
                    sharedPreferenceManager.removeUserInfo()
                }
            }
        }

        override fun removeUser() {
            // 카카오 로그아웃 / 회원탈퇴
        }
    }


}