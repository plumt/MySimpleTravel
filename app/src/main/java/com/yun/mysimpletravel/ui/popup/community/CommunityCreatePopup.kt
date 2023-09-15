package com.yun.mysimpletravel.ui.popup.community

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.databinding.DialogCommunityCreateBinding

class CommunityCreatePopup(
    private val context: Context,
    private val communityCreateInterface: CommunityCreateInterface
) {

    interface CommunityCreateInterface {
        fun onButtonClick(result: Boolean)
    }

    fun showPopup() {
        val dialog = Dialog(context)
        val view = View.inflate(context, R.layout.dialog_community_create, null)
        val binding = DialogCommunityCreateBinding.bind(view)
        dialog.setContentView(binding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window?.attributes = layoutParams

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation


        binding.btnBack.setOnClickListener {
            Log.d("lys","communityCreateInterface.onButtonClick(false)")
            communityCreateInterface.onButtonClick(false)
            dialog.dismiss()
        }

        binding.btnSave.setOnClickListener {
            Log.d("lys","communityCreateInterface.onButtonClick(true)")
            communityCreateInterface.onButtonClick(true)
            dialog.dismiss()
        }

        dialog.show()
    }
}