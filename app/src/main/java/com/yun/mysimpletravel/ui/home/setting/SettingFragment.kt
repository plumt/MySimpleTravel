package com.yun.mysimpletravel.ui.home.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.BaseRecyclerAdapter
import com.yun.mysimpletravel.data.model.setting.SettingDataModel
import com.yun.mysimpletravel.databinding.FragmentSettingBinding
import com.yun.mysimpletravel.databinding.ItemSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {
    override val viewModel: SettingViewModel by viewModels()
    override fun getResourceId(): Int = R.layout.fragment_setting
    override fun isLoading(): LiveData<Boolean> = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}
    override fun setVariable(): Int = BR.setting

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSetting.run {
            adapter = object : BaseRecyclerAdapter.Create<SettingDataModel,ItemSettingBinding>(
                layoutResId = R.layout.item_setting,
                bindingVariableId = BR.itemSetting,
                bindingListener = BR.settingListener
            ){
                override fun onItemClick(item: SettingDataModel, view: View) {
                    Log.d("lys","item click > $item")
                }

                override fun onItemLongClick(item: SettingDataModel, view: View): Boolean = true
            }
        }

    }
}