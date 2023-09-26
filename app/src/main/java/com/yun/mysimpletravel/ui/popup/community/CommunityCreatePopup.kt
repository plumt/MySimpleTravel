package com.yun.mysimpletravel.ui.popup.community

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.databinding.DialogCommunityCreateBinding
import java.io.ByteArrayOutputStream

class CommunityCreatePopup(
    private val context: Context,
    private val fragment: Fragment,
    private val communityCreateInterface: CommunityCreateInterface
) {

    private lateinit var binding: DialogCommunityCreateBinding
    private lateinit var dialog: Dialog
    private var base64 = ""

    interface CommunityCreateInterface {
        fun onButtonClick(message: String, img: String)
    }

    fun showPopup() {
        dialog = Dialog(context)
        val view = View.inflate(context, R.layout.dialog_community_create, null)
        binding = DialogCommunityCreateBinding.bind(view)
        dialog.setContentView(binding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window?.attributes = layoutParams

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation



        binding.icTwoButton.btnCancel.setOnClickListener(onClickListener)
        binding.icTwoButton.btnResult.setOnClickListener(onClickListener)
        binding.icCamera.cvCamera.setOnClickListener(onClickListener)

        dialog.show()
    }

    private val onClickListener = View.OnClickListener {
        when (it) {
            binding.icTwoButton.btnCancel -> {
                dialog.dismiss()
            }

            binding.icTwoButton.btnResult -> {
                val message = binding.etMessage.text.toString().trim()
                if(!message.isNullOrEmpty()){
                    communityCreateInterface.onButtonClick(message, base64)
                    dialog.dismiss()
                }
            }

            binding.icCamera.cvCamera -> {
                Log.d("lys","binding.icCamera.cvCamera")
                checkPermissionsAndOpenGallery()
            }
        }
    }

    private val requestPermissionLauncher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // 권한이 허용되면 갤러리 열기
            openGallery()
        } else {
            // 권한이 거부되면 사용자에게 알림을 표시하거나 다른 처리를 수행할 수 있음
            // (예: 권한 설정 화면으로 이동)
        }
    }

    private val galleryLauncher = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val currentImageUri = data.data

                try {
                    currentImageUri?.let {
                        val file = context.contentResolver.openInputStream(it)?.readBytes()
                        if (file != null) {
                            val option = BitmapFactory.Options()
                            option.inSampleSize = 4
                            val bitmap = BitmapFactory.decodeByteArray(file, 0, file.size, option)
                            val stream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            val byteArray = stream.toByteArray()
                            base64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
//                            viewModel.sendTalk(base64)


                            Glide.with(context).load(Base64.decode(base64, Base64.DEFAULT)).thumbnail(0.3f).diskCacheStrategy(
                                DiskCacheStrategy.RESOURCE).into(binding.icCamera.ivImg)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkPermissionsAndOpenGallery() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val permissionGranted = PackageManager.PERMISSION_GRANTED

//        if (ContextCompat.checkSelfPermission(context, permission) != permissionGranted) {
//            Log.d("lys","권한 없음")
//            // 권한이 없으면 권한 요청
//            requestPermissionLauncher.launch(permission)
//        } else {
//            Log.d("lys","권한 이미 있음")
//            // 이미 권한이 있는 경우 갤러리 열기
//            openGallery()
//        }


        openGallery()
    }

    private fun openGallery(){
        Log.d("lys","openGallery")
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }
}