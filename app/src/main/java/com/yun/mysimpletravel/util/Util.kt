package com.yun.mysimpletravel.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import com.yun.mysimpletravel.BuildConfig
import java.net.URLDecoder
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs

object Util {

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
}