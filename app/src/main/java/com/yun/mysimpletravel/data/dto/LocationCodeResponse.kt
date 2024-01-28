package com.yun.mysimpletravel.data.dto

import com.google.gson.annotations.SerializedName

data class LocationCodeResponse(
    @SerializedName("regcodes")
    val response: List<LocationCodeList>?
)

data class LocationCodeList(
    val code: String,
    var name: String
)