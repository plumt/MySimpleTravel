package com.yun.mysimpletravel.ui.bottomsheet.location

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseRecyclerAdapter
import com.yun.mysimpletravel.base.Item
import com.yun.mysimpletravel.base.replace
import com.yun.mysimpletravel.databinding.DialogLocationBottomSheetBinding
import com.yun.mysimpletravel.databinding.ItemLocationBinding

class LocationBottomSheet<ITEM : Item>(
    private val locationBottomSheetInterface: LocationBottomSheetInterface<ITEM>
) : BottomSheetDialogFragment() {

    interface LocationBottomSheetInterface<ITEM : Item> {
        fun onLocationItemClick(item: ITEM)
    }

    private val list = ArrayList<ITEM>()
    private lateinit var binding: DialogLocationBottomSheetBinding

    fun setLocationList(locationList: List<ITEM>) {
        if (!::binding.isInitialized) return
        list.clear()
        list.addAll(locationList)
        binding.rvBottomLocation.replace(list)
    }

    fun setLoading(loading: Boolean) {
        if (!::binding.isInitialized) return
        binding.loadingProgress.visibility = if (loading) View.VISIBLE else View.INVISIBLE
    }

    override fun getTheme(): Int = R.style.RoundBottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myDialog = super.onCreateDialog(savedInstanceState)
        myDialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog

            // 드래그로 닫히는 기능 막기
            val bottomSheet =
                dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
                behavior.isDraggable = true // 드래그 기능 막기
            }
//            if (percent) setupRatio(bottomSheetDialog)
        }
        return myDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLocationBottomSheetBinding.inflate(inflater, container, false)
        // dialog의 배경을 투명하게 설정
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.rvBottomLocation.run {
            setHasFixedSize(true)
            adapter =
                object : BaseRecyclerAdapter.Create<ITEM, ItemLocationBinding>(
                    bindingVariableId = BR.itemBottomLocation,
                    bindingListener = BR.bottomLocationListener,
                    layoutResId = R.layout.item_location
                ) {
                    override fun onItemLongClick(
                        item: ITEM,
                        view: View
                    ): Boolean = true

                    override fun onItemClick(item: ITEM, view: View) {
                        locationBottomSheetInterface.onLocationItemClick(item)
//                        dismiss()
                    }
                }
            replace(list)
        }
        dialog?.setCancelable(true)
        return binding.root
    }
}