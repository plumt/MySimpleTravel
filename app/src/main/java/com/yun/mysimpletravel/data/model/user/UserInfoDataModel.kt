package com.yun.mysimpletravel.data.model.user

data class UserInfoDataModel(
    val userId: String,
    val userName: String,
    val userProfileUrl: String?,
    val loginType: String
)