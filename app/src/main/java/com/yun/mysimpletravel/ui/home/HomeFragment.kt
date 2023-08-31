package com.yun.mysimpletravel.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_home
    override fun isLoading(): LiveData<Boolean> = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}