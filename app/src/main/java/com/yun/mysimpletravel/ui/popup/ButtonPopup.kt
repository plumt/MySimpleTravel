package com.yun.mysimpletravel.ui.popup

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.OnSingleClickListener
import com.yun.mysimpletravel.databinding.DialogButtonBinding

class ButtonPopup(private val context: Context) {

//    fun showTestPopup(
//        title: String,
//        message: String,
//        leftBtnNm: String,
//        rightBtnNm: String? = null
//    ) {
//        val dialog = Dialog(context)
//
//        // 팝업 레이아웃 설정
//        dialog.setContentView(R.layout.dialog_button)
//        val layoutParams = WindowManager.LayoutParams()
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
//        dialog.window?.attributes = layoutParams
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//
//        // 팝업 내용 설정
//        val view = View.inflate(context, R.layout.dialog_button, null)
//        val binding = DialogButtonBinding.bind(view)
//        binding.apply {
//            setVariable(BR.title, title)
//            setVariable(BR.message, message)
//            setVariable(BR.leftBtnNm, leftBtnNm)
//            setVariable(BR.rightBtnNm, rightBtnNm)
//        }
//
//        // 버튼 클릭 리스너 설정
//        binding.run {
//            if (rightBtnNm == null) {
//                btnCancel.visibility = View.GONE
//            }
//
//            btnCancel.setOnClickListener {
//                dialog.dismiss()
//            }
//            btnResult.setOnClickListener {
//                dialog.dismiss()
//            }
//        }
//
//        // 팝업 표시
//        dialog.show()
//    }

    interface ButtonDialogListener {
        fun onButtonClick(result: Boolean)
    }


    fun showPopup(
        title: String, message: String, rightBtnNm: String, leftBtnNm: String? = null,
        buttonDialogListener: ButtonDialogListener
    ) {
        AlertDialog.Builder(context).run {
            setCancelable(true)
            val view = View.inflate(context, R.layout.dialog_button, null)
            val binding = DialogButtonBinding.bind(view)
            binding.apply {
                setVariable(BR.title, title)
                setVariable(BR.message, message)
                setVariable(BR.leftBtnNm, leftBtnNm)
                setVariable(BR.rightBtnNm, rightBtnNm)
            }
            setView(binding.root)
            val dialog = create()
            dialog.apply {
                window?.requestFeature(Window.FEATURE_NO_TITLE)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.attributes?.windowAnimations = R.style.DialogAnimation
//                setOnDismissListener {
//                    myDialogListener.onResultClicked(false)
//                }

            }
            binding.run {

                if (leftBtnNm == null) {
                    icTwoButton.btnCancel.visibility = View.GONE
                }

                icTwoButton.btnCancel.setOnClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(v: View) {
                        buttonDialogListener.onButtonClick(false)
                        dialog.dismiss()
                    }
                })

                icTwoButton.btnResult.setOnClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(v: View) {
                        buttonDialogListener.onButtonClick(true)
                        dialog.dismiss()
                    }
                })
            }
            dialog
        }.show()
    }
}