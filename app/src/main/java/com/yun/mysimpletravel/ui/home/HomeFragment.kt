package com.yun.mysimpletravel.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.ZoomOutPageTransformer
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

        /**
        0 번째는 여행 관련 정보 페이지
        1 번째는
        2 번쨰는
        3 번째는 유저 정보 확인 및 앱 설정 페이지
         */

        binding.vpHome.run {
            isUserInputEnabled = false
            setPageTransformer(ZoomOutPageTransformer()) // 전환 애니메이션
            adapter = object : FragmentStateAdapter(this@HomeFragment) {
                override fun getItemCount(): Int = 4
                override fun createFragment(position: Int): Fragment =
                    when (position) {
                        TRAVEL -> TravelFragment()
                        SETTING -> SettingFragment()
                        else -> Fragment()
                    }
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d("lys", "onPageSelected > $position")
                }
            })
        }


        TabLayoutMediator(binding.tabLayout, binding.vpHome) { tab, position ->
            tab.text = when (position) {
                TRAVEL -> "TRAVEL"
                SETTING -> "SETTING"
                else -> "TAB $position"
            }
        }.attach()
    }
}