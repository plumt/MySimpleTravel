package com.yun.mysimpletravel.util

import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.common.constants.NavigationConstants

object NavigationUtil {

    fun mainScreenAnimation(nowScreen: String, moveScreen: String): NavigationConstants.Type {
        val screenMap: Map<String, Int> = mapOf(
            Pair(NavigationConstants.MainScreen.MAP, 0),
            Pair(NavigationConstants.MainScreen.DIARY, 1),
            Pair(NavigationConstants.MainScreen.HOME, 2),
            Pair(NavigationConstants.MainScreen.COMMUNITY, 3),
            Pair(NavigationConstants.MainScreen.SETTING, 4)
        )
        val nowScreenIndex = screenMap[nowScreen] ?: -1
        val moveScreenIndex = screenMap[moveScreen] ?: -1
        return if (nowScreenIndex > moveScreenIndex) NavigationConstants.Type.EXIT
        else if (nowScreenIndex < moveScreenIndex) NavigationConstants.Type.ENTER
        else NavigationConstants.Type.NOT

    }
}