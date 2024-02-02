package com.yun.mysimpletravel.data.model.jejuhub.map

import com.yun.mysimpletravel.base.Item

data class JejuHubCategoryModel(
    override var id: Int,
    val title: String,
    val isClickable: Boolean
): Item() {

}