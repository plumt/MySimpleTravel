package com.yun.mysimpletravel.data.model.location

import com.yun.mysimpletravel.base.Item

class LocationDataModel {
    data class RS(
        val regcodes: ArrayList<Items>?
    )

    data class Items(
        override var id: Int,
        val code: String,
        var name: String,
        var fullName: String
    ) : Item()
}