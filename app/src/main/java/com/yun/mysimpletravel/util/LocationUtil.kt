package com.yun.mysimpletravel.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log

object LocationUtil {

    // address to lat/lng
    fun getAddressFromLocationName(context: Context, name:String): Address? {
        return Geocoder(context).getFromLocationName(name, name.length)?.run {
            this[0]
        }
    }
}