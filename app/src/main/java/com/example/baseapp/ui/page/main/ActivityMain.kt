package com.example.baseapp.ui.page.main

import android.content.Intent
import android.view.LayoutInflater
import com.example.baseapp.databinding.ActivityMainBinding
import com.example.baseapp.ui.base.BaseActivity
import com.example.baseapp.ui.page.fixtures.ActivityScoresFixtures
import com.example.baseapp.ui.page.standings.ActivityStandings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityMain : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initData(intent: Intent) {}

    override fun initView() {
        // No special initialization needed
    }

    override fun initAction() {
       binding.cardScoresFixtures.setOnClickListener { navigateToScoresFixtures() }
        binding.cardStandings.setOnClickListener { navigateToStandings() }
    }

    private fun navigateToScoresFixtures() {
        startActivity(Intent(this, ActivityScoresFixtures::class.java))
    }
    private fun navigateToStandings() {
        startActivity(Intent(this, ActivityStandings::class.java))
    }

    override fun initObserver() {}
}