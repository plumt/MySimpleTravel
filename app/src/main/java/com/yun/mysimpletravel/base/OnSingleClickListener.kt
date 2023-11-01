package com.yun.mysimpletravel.base

import android.os.SystemClock
import android.view.View

abstract class OnSingleClickListener : View.OnClickListener {

    private val MIN_CLICK_INTERVAL = 600

    abstract fun onSingleClick(v: View)

    private var lastClickTime = 0L

    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - lastClickTime
        lastClickTime = currentClickTime
        if(elapsedTime > MIN_CLICK_INTERVAL){
            onSingleClick(v)
        }
    }
}