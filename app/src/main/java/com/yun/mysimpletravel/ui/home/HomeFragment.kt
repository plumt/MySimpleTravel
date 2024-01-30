package com.yun.mysimpletravel.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.OnSingleClickListener
import com.yun.mysimpletravel.common.constants.LocationConstants.Key.FULL_NAME
import com.yun.mysimpletravel.common.constants.LocationConstants.Key.NAME
import com.yun.mysimpletravel.common.constants.NavigationConstants
import com.yun.mysimpletravel.common.constants.WeatherConstants
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.databinding.FragmentHomeBinding
import com.yun.mysimpletravel.util.LocationUtil.getAddressFromLocationName
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_home
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.home

    @Inject
    lateinit var sPrefs: PreferenceUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observes()


    }

    private fun observes() {

        sharedVM.let { sv ->
            sv.bottomNavDoubleTab.observe(viewLifecycleOwner) { doubleTab ->
                if (doubleTab) {
                    binding.layoutParent.smoothScrollTo(0, 0)
                    sharedVM.bottomNavDoubleTabEvent()
                }
            }
        }

        viewModel.let { vm ->
            vm.moveScreen.observe(viewLifecycleOwner){
                sharedVM.moveScreens(it)
            }
        }
    }

}