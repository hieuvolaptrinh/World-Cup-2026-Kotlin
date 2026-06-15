package com.example.baseapp.ui.page.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.baseapp.R
import com.example.baseapp.databinding.ActivityMainBinding
import com.example.baseapp.ui.base.BaseActivity
import com.example.baseapp.utils.ScreenUtils

class ActivityMain : BaseActivity<ActivityMainBinding>() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
    }

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initData(intent: Intent) {
        askNotificationPermission()
    }

    override fun setApplyWindow() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)

            val navInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            if (ScreenUtils.isGestureNavigation(insets)) {
                binding.bottomInset.layoutParams.height = navInsets.bottom
                binding.bottomInset.visibility = View.VISIBLE
            } else {
                binding.bottomInset.layoutParams.height = 0
                binding.bottomInset.visibility = View.GONE
            }

            binding.viewPager.setPadding(0, systemBars.top, 0, 0)
            insets
        }
    }

    override fun initView() {
        setupBottomNavigationView()

        val pageAdapter = PagerAdapterMain(this)
        binding.apply {
            viewPager.adapter = pageAdapter
            viewPager.isUserInputEnabled = false
            viewPager.offscreenPageLimit = pageAdapter.itemCount
        }
    }

    override fun initAction() {
        binding.apply {
            bottomNav.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_home -> {
                        viewPager.setCurrentItem(PagerAdapterMain.POSITION_HOME, false)
                    }

                    R.id.menu_lib -> {
                        viewPager.setCurrentItem(PagerAdapterMain.POSITION_LIB, false)
                    }

                    R.id.menu_settings -> {
                        viewPager.setCurrentItem(PagerAdapterMain.POSITION_SETTINGS, false)
                    }
                }
                return@setOnItemSelectedListener true
            }
        }
    }

    override fun initObserver() {
    }

    private fun setupBottomNavigationView() {
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.bottomNav
        ) { v, insets ->
            v.setPadding(
                v.paddingLeft, v.paddingTop, v.paddingRight, v.paddingBottom
            )
            insets
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}