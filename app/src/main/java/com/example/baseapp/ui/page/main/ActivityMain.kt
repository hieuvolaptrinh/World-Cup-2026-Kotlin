package com.worldcup.app.ui.page.main

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.worldcup.app.databinding.ActivityMainBinding
import com.worldcup.app.ui.base.BaseActivity
import com.worldcup.app.ui.page.fixtures.ActivityScoresFixtures
import com.worldcup.app.ui.page.main.adapter.ScoreFixturesTodayAdapter
import com.worldcup.app.ui.page.standings.ActivityStandings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityMain : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private val scoreFixturesTodayAdapter = ScoreFixturesTodayAdapter()

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initData(intent: Intent) {}

    override fun initView() {
        binding.apply {
            rvScoresFixtures.apply {
                layoutManager = LinearLayoutManager(this@ActivityMain)
                adapter = scoreFixturesTodayAdapter
                isNestedScrollingEnabled = false
            }
        }
    }

    override fun initAction() {
        binding.tvViewAllScoresFixtures.setOnClickListener { navigateToScoresFixtures() }
        binding.tvViewAllStanding.setOnClickListener { navigateToStandings() }
    }

    private fun navigateToScoresFixtures() {
        startActivity(Intent(this, ActivityScoresFixtures::class.java))
    }

    private fun navigateToStandings() {
        startActivity(Intent(this, ActivityStandings::class.java))
    }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.matches.collect { matches ->
                scoreFixturesTodayAdapter.updateMatches(matches)
            }
        }
    }
}
