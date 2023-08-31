package com.yun.mysimpletravel.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.manager.KakaoManager
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel
import com.yun.mysimpletravel.databinding.FragmentLoginBinding
import com.yun.mysimpletravel.util.Util.delayedHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(),
    KakaoManager.KakaoInterface {
    override val viewModel: LoginViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_login
    override fun isLoading(): LiveData<Boolean> = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.login

    private lateinit var kakaoManager: KakaoManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kakaoManager = KakaoManager(requireActivity(), this)

        binding.ivKakaoLoginButton.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view) {
            binding.ivKakaoLoginButton -> {
                kakaoManager.kakaoLogin()
            }
        }
    }

    override fun kakaoError(t: Throwable) {
        t.printStackTrace()
    }

    override fun loginUserInfo(info: UserInfoDataModel) {
        viewModel.saveUserInfo(info)
        Log.d("lys","loginUserInfo > $info")
    }
}