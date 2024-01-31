package com.yun.mysimpletravel.common.manager

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yun.mysimpletravel.util.Util
import com.yun.mysimpletravel.util.Util.getNavigationBarHeight

class BottomSheetManager(
    private val context: Context,
    private val bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>,
    private val bottomSheetInterface: BottomSheetInterface
) {

    // 웹뷰에서 지정한 위치가 아닌, 다른 곳을 드래그 해서 바텀 시트를 닫을 수 없게 방지하는 코드
    private var isTouch = false

    interface BottomSheetInterface {
        fun isBottomSheetShow(isShow: Boolean)
    }

    fun setTouchEnable(enable: Boolean) {
        isTouch = enable
    }

    init {
        // peek height 높이 여백 주는거 지우는 코드
        bottomSheetBehavior.isGestureInsetBottomIgnored = true
//        if (getNavigationBarHeight(context) < 20) {
//            // 안드로이드 네비바 모드가 제스처일땐 높이를 좀 더 늘린다
//            //TODO 바텀 시트마다 peekHieght이 다르기 때문에 좀 더 체크해볼 필요 있음
//            //TODO 가이드북에선 peekHeigth 높다
//            bottomSheetBehavior.peekHeight = Util.dpToPx(context.resources, 80).toInt()
////            binding.layoutBottomSheetDragLine.layoutParams.height = Util.dpToPx(context.resources, 80).toInt()
////            binding.layoutBottomSheetDragLine.requestLayout()
//        }
        bottomSheetBehavior.peekHeight = Util.dpToPx(context.resources, 100).toInt()

        bottomSheetBehavior.setBottomSheetCallback(
            object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    //TODO 아래 코드는 필요할 수도 > drag 부분을 터치해야만 닫을지 말지
                    when (newState) {
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            if (!isTouch) bottomSheetBehavior.state =
                                BottomSheetBehavior.STATE_EXPANDED
                        }

                        BottomSheetBehavior.STATE_EXPANDED -> {
                            bottomSheetInterface.isBottomSheetShow(true)
                        }

                        BottomSheetBehavior.STATE_COLLAPSED, BottomSheetBehavior.STATE_HIDDEN -> {
                            bottomSheetInterface.isBottomSheetShow(false)
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    //TODO 아래 코드는 필요할 수도 > 딤처리 관련
                    bottomSheetInterface.isBottomSheetShow(slideOffset == 1f)
//                    val alpha = slideOffset * 0.5f // slideOffset을 0~1 사이의 값으로 조정하여 alpha 값 계산
//                    val color = Color.argb((alpha * 255).toInt(), 0, 0, 0) // alpha 값으로 색상 계산
//                    // 딤처리 자연스럽게 하는 코드
//                    binding.dimLayout.setBackgroundColor(color)
//                    if(slideOffset == 0f) binding.dimLayout.visibility = View.GONE
//                    else binding.dimLayout.visibility = View.VISIBLE
                }
            })
    }

}