package com.example.baseapp.ui.page.worldcup

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.baseapp.R
import com.example.baseapp.databinding.ActivityWorldcupBinding
import com.example.baseapp.ui.base.BaseActivity
import com.example.baseapp.utils.ScreenUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityWorldCup : BaseActivity<ActivityWorldcupBinding>() {

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityWorldcupBinding {
        return ActivityWorldcupBinding.inflate(layoutInflater)
    }

    override fun initData(intent: Intent) {}

    override fun setApplyWindow() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
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

        val pageAdapter = PagerAdapterWorldCup(this)
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
                    R.id.menu_worldcup_home -> {
                        viewPager.setCurrentItem(PagerAdapterWorldCup.POSITION_HOME, false)
                    }
                    R.id.menu_scores_fixtures -> {
                        viewPager.setCurrentItem(PagerAdapterWorldCup.POSITION_FIXTURES, false)
                    }
                    R.id.menu_standings -> {
                        viewPager.setCurrentItem(PagerAdapterWorldCup.POSITION_STANDINGS, false)
                    }
                }
                return@setOnItemSelectedListener true
            }
        }
    }

    override fun initObserver() {}

    private fun setupBottomNavigationView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNav) { v, insets ->
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, v.paddingBottom)
            insets
        }
    }
}
