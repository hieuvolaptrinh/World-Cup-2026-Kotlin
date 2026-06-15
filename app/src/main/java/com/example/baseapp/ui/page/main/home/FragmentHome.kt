package com.example.baseapp.ui.page.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.baseapp.databinding.FragmentHomeBinding
import com.example.baseapp.ui.base.BaseFragment
import com.example.baseapp.ui.page.worldcup.ActivityWorldCup

class FragmentHome : BaseFragment<FragmentHomeBinding>() {
    override fun getBindingInflater(
            inflater: LayoutInflater,
            container: ViewGroup?,
            attachToParent: Boolean
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initData(bundle: Bundle?) {}

    override fun initViews() {}

    override fun initActions() {
        binding.apply { btnHome.setOnClickListener { navigateToWorldCup() } }
    }

    override fun initObserver() {}

    private fun navigateToWorldCup() {
        val intent = Intent(context, ActivityWorldCup::class.java)
        startActivity(intent)
    }
}
