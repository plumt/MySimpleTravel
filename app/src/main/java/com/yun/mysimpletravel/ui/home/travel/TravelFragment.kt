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
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.TRAVEL
import com.yun.mysimpletravel.common.constants.LocationConstants
import com.yun.mysimpletravel.databinding.FragmentTravelBinding
import com.yun.mysimpletravel.ui.home.HomeViewModel
import com.yun.mysimpletravel.ui.home.ViewPagerCallback
import com.yun.mysimpletravel.util.PreferenceUtil
import com.yun.mysimpletravel.util.ViewUtil.setWeatherImages
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TravelFragment : BaseFragment<FragmentTravelBinding, TravelViewModel>(), ViewPagerCallback {
    override val viewModel: TravelViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_travel
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.travel

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onPageSelected(position: Int) {
        if (position == TRAVEL) {
            visibilityParentLayout(View.VISIBLE)
            init()
        } else {
            visibilityParentLayout(View.INVISIBLE)
        }
    }

    private fun visibilityParentLayout(visibility: Int) {
        if (isBindingInitialized) binding.layoutParent.visibility = visibility
    }

    @Inject
    lateinit var sPrefs: PreferenceUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        lifecycleScope.launch {
            val weatherInfo = viewModel.nowWeather()
        }
    }

    override fun onDestroy() {
        Log.d("lys", "onDestroy")
        super.onDestroy()
    }

    override fun onDestroyView() {
        Log.d("lys", "onDestroyView")
        super.onDestroyView()
    }

    override fun onPause() {
        Log.d("lys", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("lys", "onStop")
        super.onStop()
    }

    override fun onResume() {
        Log.d("lys", "onResume")
        super.onResume()
    }
}