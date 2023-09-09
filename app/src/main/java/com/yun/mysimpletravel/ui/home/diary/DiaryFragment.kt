package com.yun.mysimpletravel.ui.home.diary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.DIARY
import com.yun.mysimpletravel.databinding.FragmentDiaryBinding
import com.yun.mysimpletravel.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryFragment : BaseFragment<FragmentDiaryBinding, DiaryViewModel>() {
    override val viewModel: DiaryViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_diary
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.diary

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.screen.observe(viewLifecycleOwner){screen ->
            if(screen == DIARY){
                binding.layoutParent.visibility = View.VISIBLE
            } else {
                binding.layoutParent.visibility = View.INVISIBLE
            }
        }
    }
}