package com.yun.mysimpletravel.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.COMMUNITY
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.DIARY
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.SETTING
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.TRAVEL
import com.yun.mysimpletravel.common.interfaces.ViewPagerInterface
import com.yun.mysimpletravel.databinding.FragmentHomeBinding
import com.yun.mysimpletravel.ui.home.community.CommunityFragment
import com.yun.mysimpletravel.ui.home.diary.DiaryFragment
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

    lateinit var travelFragment: TravelFragment
    lateinit var diaryFragment: DiaryFragment
    lateinit var communityFragment: CommunityFragment
    lateinit var settingFragment: SettingFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSetting()

        /**
        0 번째는 여행 관련 정보 페이지
        1 번째는
        2 번쨰는
        3 번째는 유저 정보 확인 및 앱 설정 페이지
         */
        binding.vpHome.run {
            isUserInputEnabled = false
//            setPageTransformer(ZoomOutPageTransformer()) // 전환 애니메이션
            adapter = object : FragmentStateAdapter(this@HomeFragment) {
                override fun getItemCount(): Int = 4
                override fun createFragment(position: Int): Fragment =
                    screen(position) ?: Fragment()
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    travelFragment.onPageSelected(position)
                    diaryFragment.onPageSelected(position)
                    communityFragment.onPageSelected(position)
                    settingFragment.onPageSelected(position)
                }
            })
        }


        TabLayoutMediator(binding.tabLayout, binding.vpHome) { tab, position ->
            tab.text = when (position) {
                TRAVEL -> "TRAVEL"
                DIARY -> "DIARY"
                COMMUNITY -> "COMMUNITY"
                SETTING -> "SETTING"
                else -> "TAB $position"
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (val position = tab?.position) {
                    TRAVEL -> travelFragment.onReselected(position)
                    DIARY -> diaryFragment.onReselected(position)
                    COMMUNITY -> communityFragment.onReselected(position)
                    SETTING -> settingFragment.onReselected(position)
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

        })
    }

//    private val viewPagerInterface = object : ViewPagerInterface {
//        override fun moveScreen(position: Int) {}
//        override fun onReselected(position: Int) {}
//        override fun onPageSelected(position: Int) {
//            Log.d("lys","onPageSelected > $position")
//        }
//    }

    private fun fragmentSetting() {
        travelFragment = TravelFragment(object : ViewPagerInterface {
            override fun onPageSelected(position: Int) {}
            override fun onReselected(position: Int) {}
            override fun moveScreen(position: Int) {
                binding.vpHome.setCurrentItem(position, true)
            }
        })
        diaryFragment = DiaryFragment()
        communityFragment = CommunityFragment()
        settingFragment = SettingFragment()
    }

    private fun screen(position: Int): Fragment? = when (position) {
        TRAVEL -> travelFragment
        DIARY -> diaryFragment
        COMMUNITY -> communityFragment
        SETTING -> settingFragment
        else -> null
    }
}