package com.yun.mysimpletravel.base

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yun.mysimpletravel.R

class BaseBottomNavi {

    companion object {
        @BindingAdapter("app:onNavigationItemSelected")
        @JvmStatic
        fun setOnNavigationItemSelectedListener(
            view: BottomNavigationView,
            listener: (View, String) -> Boolean
        ) {
            view.setOnItemSelectedListener {
                listener(view, it.title as String)
                true
            }

        }
    }
}