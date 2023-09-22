package com.yun.mysimpletravel.ui.diary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.databinding.FragmentDiaryBinding
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class DiaryFragment : BaseFragment<FragmentDiaryBinding, DiaryViewModel>() {
    override val viewModel: DiaryViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_diary
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.diary

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("lys","onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun init() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("lys","onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDetach() {
        Log.d("lys","onDetach")
        super.onDetach()
    }

    override fun onStop() {
        Log.d("lys","onStop")
        super.onStop()
    }
    override fun onResume() {
        Log.d("lys","onResume")
        super.onResume()
    }

    override fun onDestroyView() {
        Log.d("lys","onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("lys","onDestroy")
        super.onDestroy()

    }

}