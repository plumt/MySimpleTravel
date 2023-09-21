package com.yun.mysimpletravel.common.constants

class LocationConstants {

    object Key {
        const val NAME = "name"
        const val FULL_NAME = "full_name"
    }

    object SearchCode {
        const val ALL = "*00000000"
        const val JEJU_ALL = "50*000000"
        const val JEJU_JEJU = "5011*000"
        const val JEJU_SEOGWIP = "5013*000"
    }

    object LocationCode {
        const val JEJU_PROVINCE = "5000000000"
        const val JEJU = "5011000000"
        const val SEOGWIP = "5013000000"
    }

    object FilterName {
        const val JEJU_PROVINCE = "제주특별자치도"
        const val JEJU = "제주시"
        const val SEOGWIP = "서귀포시"
    }

}