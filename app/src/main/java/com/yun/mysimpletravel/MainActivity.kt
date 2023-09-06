package com.yun.mysimpletravel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yun.mysimpletravel.databinding.ActivityMainBinding
import com.yun.mysimpletravel.ui.loading.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            main = mainViewModel
        }
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        loadingDialog = LoadingDialog(this)

        // loading dialog show / hide
        mainViewModel.isLoading.observe(this) { isShow ->
            if (isShow) loadingDialog.show() else loadingDialog.dismiss()
        }
    }
}