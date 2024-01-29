package com.yun.mysimpletravel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yun.mysimpletravel.common.constants.NavigationConstants
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.databinding.ActivityMainBinding
import com.yun.mysimpletravel.ui.loading.LoadingDialog
import com.yun.mysimpletravel.util.NavigationUtil.mainScreenAnimation
import com.yun.mysimpletravel.util.Util.changeStatusBarAndScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    lateinit var loadingDialog: LoadingDialog
    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        observes()
    }

    /**
     * 초기 설정
     */
    private fun init() {
        installSplashScreen()
        changeStatusBarAndScreen(this, isIconColor = true, isFullScreen = false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            main = mainViewModel
        }
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        loadingDialog = LoadingDialog(this)
        binding.bottomNavi.background = null
        navigationManager = NavigationManager(findViewById(R.id.nav_host_fragment))
        homeScreenClickedEffect()
        binding.bottomNavi.menu.forEach {
            findViewById<View>(it.itemId).setOnLongClickListener { true }
        }
    }

    /**
     * 라이브 데이터 구독 모음
     */
    private fun observes() {

        mainViewModel.let { vm ->

            vm.isLoading.observe(this) { isShow ->
                // loading dialog show / hide
                if (isShow) loadingDialog.show() else loadingDialog.dismiss()
            }

            vm.moveScreen.observe(this) { screen ->
                screen?.let {
                    val screenId = when (it) {
                        NavigationConstants.MainScreen.HOME -> R.id.global_homeFragment
                        NavigationConstants.MainScreen.MAP -> R.id.global_mapFragment
                        NavigationConstants.MainScreen.DIARY -> R.id.global_diaryFragment
                        NavigationConstants.MainScreen.COMMUNITY -> R.id.global_communityFragment
                        NavigationConstants.MainScreen.SETTING -> R.id.global_settingFragment
                        else -> return@observe
                    }
                    navigationManager.movingScreen(
                        screenId,
                        mainScreenAnimation(
                            navController.currentDestination?.label?.toString() ?: "", screen
                        )
                    )
                    if (it == NavigationConstants.MainScreen.HOME) homeScreenClickedEffect()
                }
            }
        }
    }

    private fun homeScreenClickedEffect() {
        binding.bottomNavi.menu.getItem(2).isEnabled = false
        binding.bottomNavi.menu.getItem(2).isChecked = true
    }
}