package com.yun.mysimpletravel.data.model.community

import com.google.firebase.Timestamp
import com.yun.mysimpletravel.base.Item
import java.text.SimpleDateFormat
import java.util.Date

class CommunityDataModel {
    data class RS(
        override var id: Int,
        val imageUrl: String,
        var likes: ArrayList<String>?,
        var like: Boolean,
        val message: String,
        val timestamp: Timestamp,
        val writer: String,
        var fullSize: Boolean
    ) : Item() {
        fun dateConvert(date: Timestamp): String {
            val formatter = SimpleDateFormat("a h:m")
            return formatter.format(Date(date.seconds * 1000))
        }

        fun likeCnt(likes: ArrayList<String>?) = likes?.size ?: 0
    }
}