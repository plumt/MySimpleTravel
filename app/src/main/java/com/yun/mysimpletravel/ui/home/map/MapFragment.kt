package com.yun.mysimpletravel.ui.home.map

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.HomeConstants
import com.yun.mysimpletravel.common.interfaces.ViewPagerInterface
import com.yun.mysimpletravel.databinding.FragmentMapBinding
import com.yun.mysimpletravel.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(), ViewPagerInterface {
    override val viewModel: MapViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_map
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.map

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                // 지도 API 가 정상적으로 종료될 때 호출됨
                Log.d("lys-map","onMapDestroy")
            }

            override fun onMapError(error: Exception?) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
                Log.e("lys-map","onMapError > ${error?.message}")

            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                // 인증 후 API 가 정상적으로 실행될 때 호출됨
                Log.d("lys-map","onMapReady")

            }
        })

    }

    private fun visibilityParentLayout(visibility: Int) {
        if (isBindingInitialized) binding.layoutParent.visibility = visibility
    }

    override fun moveScreen(position: Int) {}
    override fun onReselected(position: Int) {}
    override fun onPageSelected(position: Int) {
        if (position == HomeConstants.Screen.MAP) {
            visibilityParentLayout(View.VISIBLE)
        } else {
            visibilityParentLayout(View.INVISIBLE)
        }
    }
}