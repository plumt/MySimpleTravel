package com.yun.mysimpletravel.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.KakaoMapConstants
import com.yun.mysimpletravel.common.manager.KakaoMapManager
import com.yun.mysimpletravel.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(), KakaoMapManager.KakaoMapInterface {
    override val viewModel: MapViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_map
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {
        Log.d("lys", "back!")
    }

    override fun setVariable(): Int = BR.map

    private lateinit var mapView: MapView

    private lateinit var kakaoMapManager: KakaoMapManager
//    private lateinit var bottomSheetManager: BottomSheetManager
    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
    }

    private fun observe(){
        viewModel.let { vm ->
            vm.carsharingWithSocar.observe(viewLifecycleOwner) {
                it?.list?.let { data ->
                    if (!this::mapView.isInitialized || !this::kakaoMapManager.isInitialized) return@observe
                    Log.d("lys","_socr carsharing data > ${data.size}")
                    data.forEachIndexed { index, item ->
                        kakaoMapManager.addMarker(
                            item.latitude.toDouble(),
                            item.longitude.toDouble(),
                            item.placeName,
                            KakaoMapConstants.MarkerType.Sorca
                        )
                    }
                    kakaoMapManager.mapBounds()
                }
            }

            vm.carsharing.observe(viewLifecycleOwner){
                it?.list?.let { data ->
                    Log.d("lys","_carsharing data > ${data.size}")
                    if (!this::mapView.isInitialized || !this::kakaoMapManager.isInitialized) return@observe
                    data.forEachIndexed { index, item ->
                        kakaoMapManager.addMarker(
                            item.latitude.toDouble(),
                            item.longitude.toDouble(),
                            item.placeName,
                            KakaoMapConstants.MarkerType.CarShare
                        )
                    }
                    kakaoMapManager.mapBounds()
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        if (!this::mapView.isInitialized) {
            mapView = MapView(requireActivity())
            binding.mapView.addView(mapView)
            kakaoMapManager = KakaoMapManager(mapView,this)
        }
    }


    override fun onDestroyView() {
        binding.mapView.removeView(mapView)
        super.onDestroyView()
    }
}