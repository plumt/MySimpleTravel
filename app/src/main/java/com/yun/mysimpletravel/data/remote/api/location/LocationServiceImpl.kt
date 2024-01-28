package com.yun.mysimpletravel.data.remote.api.location

import com.yun.mysimpletravel.data.dto.LocationCodeResponse
import com.yun.mysimpletravel.data.model.location.LocationModel

object LocationServiceImpl {
    fun LocationCodeResponse.toLocationModelList(): List<LocationModel>? {
        return this.response?.map {
            LocationModel(
                id = 0,
                code = it.code,
                name = it.name
            )
        }
    }
}