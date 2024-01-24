package com.yun.mysimpletravel.base

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class BaseBottomNavi {

    companion object {
        @BindingAdapter("app:onNavigationItemSelected")
        @JvmStatic
        fun setOnNavigationItemSelectedListener(
            view: BottomNavigationView,
            listener: (View, Int) -> Boolean
        ) {
            view.setOnItemSelectedListener {
                listener(view, it.itemId)
                true
            }

        }
    }
}