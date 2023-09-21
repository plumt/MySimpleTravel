package com.yun.mysimpletravel.ui.travel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.interfaces.ViewPagerInterface
import com.yun.mysimpletravel.databinding.FragmentTravelBinding
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TravelFragment : BaseFragment<FragmentTravelBinding, TravelViewModel>() {
    override val viewModel: TravelViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_travel
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.travel

    @Inject
    lateinit var sPrefs: PreferenceUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}