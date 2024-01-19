package com.yun.mysimpletravel.data.model.location

import com.yun.mysimpletravel.base.Item

data class LocationModel(
    override var id: Int,
    val code: String,
    var name: String,
    var fullName: String
) : Item()