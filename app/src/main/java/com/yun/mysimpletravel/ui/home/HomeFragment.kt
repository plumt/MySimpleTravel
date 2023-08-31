package com.yun.mysimpletravel.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.SETTING
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.TRAVEL
import com.yun.mysimpletravel.databinding.FragmentHomeBinding
import com.yun.mysimpletravel.ui.home.setting.SettingFragment
import com.yun.mysimpletravel.ui.home.travel.TravelFragment
import dagger.hilt.android.AndroidEntryPoint

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

        binding.vpHome.run {
            isUserInputEnabled = false
            adapter = object : FragmentStateAdapter(this@HomeFragment) {
                override fun getItemCount(): Int = 4
                override fun createFragment(position: Int): Fragment =
                    when (position) {
                        TRAVEL -> TravelFragment()
                        SETTING -> SettingFragment()
                        else -> Fragment()
                    }
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.vpHome) { tab, position ->
            tab.text = when (position) {
                TRAVEL -> "HOME"
                SETTING -> "SETTING"
                else -> "TAB $position"
            }
        }.attach()
    }
}