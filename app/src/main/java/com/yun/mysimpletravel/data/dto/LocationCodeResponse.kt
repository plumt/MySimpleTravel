package com.yun.mysimpletravel.data.dto

import com.google.gson.annotations.SerializedName
import com.yun.mysimpletravel.data.model.location.LocationModel

data class LocationCodeResponse(
    @SerializedName("regcodes")
    val response: List<LocationModel>?
)