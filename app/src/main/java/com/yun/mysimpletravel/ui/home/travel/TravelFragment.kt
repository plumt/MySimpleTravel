package com.yun.mysimpletravel.ui.home.travel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.SETTING
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.TRAVEL
import com.yun.mysimpletravel.common.constants.LocationConstants.Key.FULL_NAME
import com.yun.mysimpletravel.common.interfaces.ViewPagerInterface
import com.yun.mysimpletravel.databinding.FragmentTravelBinding
import com.yun.mysimpletravel.ui.home.HomeViewModel
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TravelFragment(private val viewPagerInterface: ViewPagerInterface) :
    BaseFragment<FragmentTravelBinding, TravelViewModel>(), ViewPagerInterface {
    override val viewModel: TravelViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_travel
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.travel

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    private fun visibilityParentLayout(visibility: Int) {
        if (isBindingInitialized) binding.layoutParent.visibility = visibility
    }

    @Inject
    lateinit var sPrefs: PreferenceUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icNowWeather.cvNowWeather.setOnClickListener(clickListener)
    }

    private fun init() {
        callNowWeather()
    }

    private val clickListener = View.OnClickListener { v ->
        when (v) {
            binding.icNowWeather.cvNowWeather -> {
                if (sPrefs.getString(requireActivity(), FULL_NAME).isNullOrEmpty()) {
                    viewPagerInterface.moveScreen(SETTING)
                } else if (!viewModel.isWeatherLoading.value!!) {
                    callNowWeather()
                }
            }
        }
    }

    override fun moveScreen(position: Int) {}
    override fun onReselected(position: Int) {
        if (position == TRAVEL && isBindingInitialized)
            binding.layoutParent.smoothScrollTo(0, 0)
    }

    override fun onPageSelected(position: Int) {
        if (position == TRAVEL) {
            visibilityParentLayout(View.VISIBLE)
            init()
        } else {
            visibilityParentLayout(View.INVISIBLE)
        }
    }

    private fun callNowWeather() {
        lifecycleScope.launch {
            val weatherInfo = viewModel.nowWeather()
        }
    }
}