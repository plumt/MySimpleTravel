package com.yun.mysimpletravel.util

import android.net.Uri
import android.util.Base64
import android.util.TypedValue
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.util.Util.dpToPx


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

    @BindingAdapter("setCommunityImages")
    @JvmStatic
    fun ImageView.setCommunityImages(base64: String?) {
        if(base64.isNullOrEmpty()) return
        Glide.with(context)
            .load(Base64.decode(base64, Base64.DEFAULT))
//            .override(SIZE_ORIGINAL)
            .placeholder(R.color.white)
            .transition(DrawableTransitionOptions.withCrossFade())

//            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(this)
    }

    @BindingAdapter("setWeatherImages")
    @JvmStatic
    fun ImageView.setWeatherImages(path: String?) {
        if (path == null) return
        try {
            GlideToVectorYou
                .init()
                .with(context)
                .setPlaceHolder(R.color.white, R.color.white)
                .load(Uri.parse(path), this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    @BindingAdapter("setCommunity")
//    @JvmStatic
//    fun ImageView.setCommunity(url: String?) {
//        try {
//            Glide.with(this)
//                .load(url)
//                .override(SIZE_ORIGINAL)
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)
//                .into(this)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    @BindingAdapter("setWidth")
    @JvmStatic
    fun ImageView.setWidth(width: String) {
        val layoutParams = this.layoutParams
        val widthPx = dpToPx(width, this)
        layoutParams.width = widthPx
        this.layoutParams = layoutParams
    }

    @BindingAdapter("setHeight")
    @JvmStatic
    fun ImageView.setHeight(height: String) {
        val layoutParams = this.layoutParams
        val heightPx = dpToPx(height, this)
        layoutParams.height = heightPx
        this.layoutParams = layoutParams
    }


}