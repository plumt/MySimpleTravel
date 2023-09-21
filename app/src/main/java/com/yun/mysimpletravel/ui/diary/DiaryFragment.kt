package com.yun.mysimpletravel.ui.diary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.databinding.FragmentDiaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryFragment : BaseFragment<FragmentDiaryBinding, DiaryViewModel>() {
    override val viewModel: DiaryViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_diary
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.diary

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun init() {

    }

}