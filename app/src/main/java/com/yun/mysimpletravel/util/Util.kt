package com.yun.mysimpletravel.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.yun.mysimpletravel.BuildConfig
import com.yun.mysimpletravel.common.constants.WeatherConstants.State.BAD
import com.yun.mysimpletravel.common.constants.WeatherConstants.State.GOOD
import com.yun.mysimpletravel.common.constants.WeatherConstants.State.NOMAL
import com.yun.mysimpletravel.common.constants.WeatherConstants.State.WORST
import java.net.URLDecoder
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs

object Util {

    /**
     * 상태바 색상 및 전체화면 상태 변경
     * isIconColor > true : black, false : white
     */
    fun changeStatusBarAndScreen(activity: Activity, isIconColor: Boolean, isFullScreen: Boolean) {
        activity.window.apply {
            statusBarColor = Color.TRANSPARENT
//            //상태바 아이콘(true: 검정 / false: 흰색)
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars =
                isIconColor
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowCompat.setDecorFitsSystemWindows(this, !isFullScreen)
            } else {
                if (isFullScreen) decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                else decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
    }

    /**
     * dp를 px로 변경
     *
     * @param resources
     * @param nValue
     * @return
     */
    @JvmStatic
    fun dpToPx(resources: Resources, nValue: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            nValue.toFloat(),
            resources.displayMetrics
        )
    }

    /**
     * 네비바 높이바
     */
    fun getNavigationBarHeight(context: Context): Int {
        val resourceId =
            context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId: Int =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimension(resourceId).toInt()
//            val px = context.resources.getDimensionPixelSize(resourceId)
//            val density = context.resources.displayMetrics.density
//            result = (px / density).toInt()
        }
        return result
    }

    /**
     * 두 시간의 차이를 리턴
     */
    fun calculateTimeDifferenceInSeconds(targetTime: LocalDateTime): Long {
        val currentTime = LocalDateTime.now()
        val differenceInSeconds = abs(ChronoUnit.MILLIS.between(currentTime, targetTime) / 1000.0)
        return (if (differenceInSeconds <= 2) {
            2.0 - differenceInSeconds
        } else {
            differenceInSeconds
        } * 1000).toLong()
    }

    /**
     * 지정한 시간 이후 콜백
     */
    fun delayedHandler(delayMillis: Long, action: () -> Unit) {
        Handler(Looper.myLooper()!!).postDelayed(action, delayMillis)
    }

    /**
     * 인터넷 연결 상태 확인
     */
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun dpToPx(dp: String, view: View) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        view.resources.displayMetrics
    ).toInt()

    fun serviceKey() = URLDecoder.decode(BuildConfig.SERVICE_KEY, "UTF-8")

    /**
     * 현재 api url이 open api url인지 체크
     * @return true : is open api
     * @return false : is not open api
     */
    fun openApiCheck(url: String?) = when {
        url.isNullOrEmpty() -> false
        url.contains(BuildConfig.JEJU_HUB_URL) -> true
        url.contains(BuildConfig.JEJU_VISIT_URL) -> true
        url.contains(BuildConfig.JEJU_ITS_URL) -> true
        else -> false
    }

    fun hideNavigationBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = activity.window.insetsController
            controller?.hide(WindowInsets.Type.navigationBars())
        } else {
            activity.window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    fun showNavigationBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = activity.window.insetsController
            controller?.show(WindowInsets.Type.navigationBars())
        } else {
            activity.window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
    }

    // 전체 화면으로 설정
    fun setFullScreen(activity: Activity) {
        activity.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    // 전체 화면 설정 취소
    fun exitFullScreen(activity: Activity) {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}