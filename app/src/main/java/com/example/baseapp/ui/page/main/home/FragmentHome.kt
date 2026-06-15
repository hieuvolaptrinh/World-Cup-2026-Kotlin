package com.example.baseapp.ui.page.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.baseapp.databinding.FragmentHomeBinding
import com.example.baseapp.ui.base.BaseFragment
import com.example.baseapp.ui.page.top20coin.ActivityTop20Coin

class FragmentHome : BaseFragment<FragmentHomeBinding>() {
    override fun getBindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initData(bundle: Bundle?) {
    }

    override fun initViews() {
    }

    override fun initActions() {
        binding.apply {
            btnHome.setOnClickListener {
                navigateToTop20Coin()
            }
        }
    }

    override fun initObserver() {
    }

    private fun navigateToTop20Coin() {
        val intent = Intent(context, ActivityTop20Coin::class.java)
        startActivity(intent)
    }
}