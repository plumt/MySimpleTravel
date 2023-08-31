package com.yun.mysimpletravel.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.yun.mysimpletravel.R

object ViewUtil {

    @BindingAdapter("setProfile")
    @JvmStatic
    fun ImageView.setProfile(url: String?) {
        try {
            Glide.with(this)
                .load(url)
                .override(SIZE_ORIGINAL)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}