package com.yun.mysimpletravel.common.manager

import com.yun.mysimpletravel.common.constants.KakaoMapConstants
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapPolyline
import net.daum.mf.map.api.MapView

class KakaoMapManager(
    private val mapView: MapView,
    private val kakaoMapInterface: KakaoMapInterface,
) {

    interface KakaoMapInterface {
    }

    private val mapPOIItems = MapPolyline()

    /**
     * marker 추가 함수
     */
    fun addMarker(lat: Double, lon: Double, name: String, type: KakaoMapConstants.MarkerType) {
        val uNowPosition = MapPoint.mapPointWithGeoCoord(lat, lon)
        val marker = MapPOIItem()
        marker.itemName = name
        marker.mapPoint = uNowPosition
        marker.markerType = when(type){
            KakaoMapConstants.MarkerType.Sorca -> MapPOIItem.MarkerType.BluePin
            KakaoMapConstants.MarkerType.CarShare -> MapPOIItem.MarkerType.RedPin
            else -> MapPOIItem.MarkerType.RedPin
        }
        mapView.addPOIItem(marker)
        mapPOIItems.addPoint(marker.mapPoint)
    }


    fun mapBounds() {
        mapView.moveCamera(
            CameraUpdateFactory.newMapPointBounds(
                MapPointBounds(mapPOIItems.mapPoints),
                50
            )
        )
    }

}