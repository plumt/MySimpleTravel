package com.yun.mysimpletravel.ui.home.diary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.DIARY
import com.yun.mysimpletravel.databinding.FragmentDiaryBinding
import com.yun.mysimpletravel.ui.home.HomeViewModel
import com.yun.mysimpletravel.ui.home.ViewPagerCallback
import com.yun.mysimpletravel.ui.popup.ButtonPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryFragment : BaseFragment<FragmentDiaryBinding, DiaryViewModel>(), ViewPagerCallback {
    override val viewModel: DiaryViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_diary
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.diary

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onPageSelected(position: Int) {
        if (position == DIARY) {
            visibilityParentLayout(View.VISIBLE)
        } else {
            visibilityParentLayout(View.INVISIBLE)
        }
    }

    override fun onReselected() {

    }

    private fun visibilityParentLayout(visibility: Int) {
        if (isBindingInitialized) binding.layoutParent.visibility = visibility
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun init() {

    }
}