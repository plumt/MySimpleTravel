package com.yun.mysimpletravel.data.model.setting

import com.yun.mysimpletravel.base.Item

data class SettingDataModel(
    override var id: Int,
    val viewType: Int,
    val title: String,
    val contents: String
) : Item()