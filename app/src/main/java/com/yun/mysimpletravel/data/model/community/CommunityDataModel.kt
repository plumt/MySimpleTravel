package com.yun.mysimpletravel.data.model.community

import com.yun.mysimpletravel.base.Item
import com.yun.mysimpletravel.data.model.user.UserInfoDataModel

class CommunityDataModel {
    data class RS(
        override var id: Int,
        val userInfo: UserInfoDataModel,
        val title: String,
        val contents: String,
        val fileUrl: String
    ) : Item()
}