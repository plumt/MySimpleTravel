package com.yun.mysimpletravel.ui.map

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.HomeConstants
import com.yun.mysimpletravel.common.interfaces.ViewPagerInterface
import com.yun.mysimpletravel.databinding.FragmentMapBinding
import com.yun.mysimpletravel.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>() {
    override val viewModel: MapViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_map
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {
        Log.d("lys","back!")
    }
    override fun setVariable(): Int = BR.map

    lateinit var mapView: MapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = MapView(requireActivity())
        binding.mapView.addView(mapView)


    }

}