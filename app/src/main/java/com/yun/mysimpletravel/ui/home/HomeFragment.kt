package com.yun.mysimpletravel.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.HomeConstants
import com.yun.mysimpletravel.common.constants.LocationConstants
import com.yun.mysimpletravel.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_home
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        binding.icNowWeather.cvNowWeather.setOnClickListener(clickListener)

    }

    private fun init() {
        callNowWeather()
    }

    private fun callNowWeather() {
        lifecycleScope.launch {
            val weatherInfo = viewModel.nowWeather()
        }
    }

    private val clickListener = View.OnClickListener { v ->
        when (v) {
            binding.icNowWeather.cvNowWeather -> {
//                if (sPrefs.getString(requireActivity(), LocationConstants.Key.FULL_NAME).isNullOrEmpty()) {
//                    viewPagerInterface.moveScreen(HomeConstants.Screen.SETTING)
//                } else if (!viewModel.isWeatherLoading.value!!) {
//                    callNowWeather()
//                }
            }
        }
    }

}