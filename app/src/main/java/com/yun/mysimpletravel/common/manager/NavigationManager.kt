package com.yun.mysimpletravel.common.manager

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.common.constants.NavigationConstants
import com.yun.mysimpletravel.common.constants.NavigationConstants.Type.ENTER
import com.yun.mysimpletravel.common.constants.NavigationConstants.Type.EXIT
import com.yun.mysimpletravel.common.constants.NavigationConstants.Type.NOT

class NavigationManager(private val view: View) {

    fun backPressed() {
        view.findNavController().popBackStack()
    }

    fun movingScreen(screenId: Int, type: NavigationConstants.Type = NavigationConstants.Type.NOT, bundle: Bundle? = null) {
        val navOption = when (type) {
            ENTER -> enterScreen()
            EXIT -> exitScreen()
            NOT -> null
        }
        try {
            view.findNavController().navigate(screenId, bundle, navOption)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun enterScreen(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right) // B 화면이 들어오는 애니메이션
            .setExitAnim(R.anim.slide_out_left) // A 화면이 나가는 애니메이션
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
    }

    private fun exitScreen(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_left) // B 화면이 들어오는 애니메이션
            .setExitAnim(R.anim.slide_out_right) // A 화면이 나가는 애니메이션
            .build()
    }
}