package com.yun.mysimpletravel.ui.home.travel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.databinding.FragmentTravelBinding
import com.yun.mysimpletravel.ui.home.HomeViewModel
import com.yun.mysimpletravel.util.ViewUtil.setWeatherImages
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TravelFragment : BaseFragment<FragmentTravelBinding, TravelViewModel>() {
    override val viewModel: TravelViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_travel
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.travel

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {

            val weatherInfo = viewModel.nowWeather()
            if (weatherInfo != null) {

                binding.tvWeather.text =
                    "날씨 : ${weatherInfo.currentWeather}\n온도 : ${weatherInfo.currentTemperature}\n상태 : ${weatherInfo.currentWeatherDetail}"
                binding.ivWeather.setWeatherImages(weatherInfo.currentImagePath)
            }

        }
    }
}