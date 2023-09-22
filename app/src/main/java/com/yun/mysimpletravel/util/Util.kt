package com.yun.mysimpletravel.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
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

    fun fullScreen(activity: Activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        activity.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }

    fun changeStatusBarAndScreen(activity: Activity, isIconColor: Boolean, isFullScreen: Boolean) {
        activity.window.apply {
            statusBarColor = Color.TRANSPARENT
//            //상태바 아이콘(true: 검정 / false: 흰색)
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars =
                isIconColor
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                setDecorFitsSystemWindows(!isFullScreen)
            } else {
                if (isFullScreen) decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                else decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
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
            0.0
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

    fun dustCheck(str: String?): Int {
        return if (str == null) 0
        else when {
            str.contains("좋음") -> GOOD
            str.contains("보통") -> NOMAL
            str.contains("매우") -> WORST
            str.contains("나쁨") -> BAD
            else -> 0
        }
    }

    fun uvCheck(str: String?): Int {
        return if (str == null) 0
        else when {
            str.contains("좋음") -> GOOD
            str.contains("보통") -> NOMAL
            str.contains("매우") -> WORST
            str.contains("높음") -> BAD
            else -> 0
        }
    }
}