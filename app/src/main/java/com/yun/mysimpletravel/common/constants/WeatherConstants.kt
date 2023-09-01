package com.yun.mysimpletravel.common.constants

class WeatherConstants {
    object Category {
        const val T1H = "T1H"   // 기온 ℃
        const val RN1 = "RN1"   // 1시간 강수량 mm
        const val PCP = "PCP"   // 1시간 강수량 mm
        const val REH = "REH"   // 습도 %
        const val PTY = "PTY"   // 강수형태 코드값
        const val VEC = "VEC"   // 풍향 deg
        const val WSD = "WSD"   // 풍속 m/s
        const val POP = "POP"   // 강수확률 %
        const val SKY = "SKY"   // 하늘상태 코드값
        const val TMP = "TMP"   // 1시간 기온 ℃
        const val TMN = "TMN"   // 일 최저 기온 ℃
        const val TMX = "TMX"   // 일 최고 기온 ℃
    }

    object Pty {
        const val NOT = "0"             // 없음
        const val RAIN = "1"            // 비 > 비
        const val RAIN_SNOW = "2"       // 비/눈 > 비
        const val SNOW = "3"            // 눈 > 눈
        const val SHOWER = "4"          // 소나기 > 비
        const val RAINDROP = "5"        // 빗방울 > 비
        const val RAINDROP_SNOW = "6"   // 빗방울눈날림 > 비
        const val SNOW_FLYING = "7"     // 눈날림 > 눈
    }

    object Sky {
        const val SUNNY = "1"       // 맑음
        const val CLOUDY = "3"      // 구름 많음 > 흐림
        const val OVERCAST = "4"    // 흐림 > 흐림
    }
}