package com.yun.mysimpletravel

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yun.mysimpletravel.common.constants.NavigationConstants
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.databinding.ActivityMainBinding
import com.yun.mysimpletravel.ui.loading.LoadingDialog
import com.yun.mysimpletravel.util.Util.changeStatusBarAndScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    lateinit var loadingDialog: LoadingDialog
    private lateinit var navigationManager: NavigationManager

    private val screenIndex = mapOf(
        R.id.global_mapFragment to 1,
        R.id.global_diaryFragment to 2,
        R.id.global_homeFragment to 3,
        R.id.action_global_communityFragment to 4,
        R.id.global_settingFragment to 5
    )

    private var nowIndex = R.id.global_homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        clickListenerSetting()
        homeScreenClickEvent()

        observes()

        binding.bottomNavi.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.map -> {
                    // Map Screen
                    moveMainScreen(
                        nowIndex,
                        R.id.global_mapFragment
                    )
                    true
                }

                R.id.diary -> {
                    // Diary Screen
                    moveMainScreen(
                        nowIndex,
                        R.id.global_diaryFragment
                    )
                    true
                }

                R.id.community -> {
                    // Community Screen
                    moveMainScreen(
                        nowIndex,
                        R.id.action_global_communityFragment
                    )
                    true
                }

                R.id.setting -> {
                    // More Screen
                    moveMainScreen(
                        nowIndex,
                        R.id.global_settingFragment
                    )
                    true
                }

                else -> false
            }
        }

        findViewById<View>(R.id.map).setOnLongClickListener { true }
        findViewById<View>(R.id.diary).setOnLongClickListener { true }
        findViewById<View>(R.id.community).setOnLongClickListener { true }
        findViewById<View>(R.id.setting).setOnLongClickListener { true }
    }

    /**
     * 초기 설정
     */
    private fun init() {
        changeStatusBarAndScreen(this, isIconColor = true, isFullScreen = false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            main = mainViewModel
        }
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        loadingDialog = LoadingDialog(this)
        binding.bottomNavi.background = null

        navigationManager = NavigationManager(this, findViewById(R.id.nav_host_fragment))
    }

    /**
     * 클릭 이벤트 리스너
     */
    private val onClickListener = View.OnClickListener {
        when (it) {
            binding.fabHome -> {
                homeScreenClickEvent()
                moveMainScreen(
                    nowIndex,
                    R.id.global_homeFragment
                )
            }
        }
    }

    /**
     * 라이브 데이터 구독 모음
     */
    private fun observes() {

        mainViewModel.let {

            it.isLoading.observe(this) { isShow ->
                // loading dialog show / hide
                if (isShow) loadingDialog.show() else loadingDialog.dismiss()
            }

//            it.bottomNavDoubleTab.observe(this) { doubleTab ->
//                Log.d("lys","doubleTab > $doubleTab")
//                // bottom nav double tab
//                if (doubleTab) mainViewModel.bottomNavDoubleTab()
//            }

        }
    }

    /**
     * 클릭 이벤트 리스너 셋팅
     */
    private fun clickListenerSetting() {
        binding.fabHome.setOnClickListener(onClickListener)
    }

    /**
     * 메인 홈 화면 버튼 클릭 이벤트
     */
    private fun homeScreenClickEvent() {
        binding.bottomNavi.menu.getItem(2).isEnabled = false
        binding.bottomNavi.menu.getItem(2).isChecked = true
    }

    private fun moveMainScreen(from: Int?, to: Int) {
        val fromIndex = screenIndex[from]
        val toIndex = screenIndex[to]
        if (fromIndex == toIndex) {
            mainViewModel.bottomNavDoubleTabEvent(true)
            return
        }
        val animation =
            if (from == null || fromIndex == null || toIndex == null) NavigationConstants.Type.NOT
            else if (fromIndex < toIndex) NavigationConstants.Type.ENTER else NavigationConstants.Type.EXIT

        navigationManager.movingScreen(to, animation)
        nowIndex = to

    }
}