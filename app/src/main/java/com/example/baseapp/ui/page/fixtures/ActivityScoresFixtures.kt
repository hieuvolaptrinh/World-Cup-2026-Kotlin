package com.example.baseapp.ui.page.fixtures

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseapp.databinding.ActivityScoresFixturesBinding

import com.example.baseapp.ui.base.BaseActivity
import com.example.baseapp.ui.page.fixtures.adapter.MatchSectionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityScoresFixtures : BaseActivity<ActivityScoresFixturesBinding>() {

    private val viewModel: ScoresFixturesViewModel by viewModels()
    private val sectionAdapter = MatchSectionAdapter()

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityScoresFixturesBinding {
        return ActivityScoresFixturesBinding.inflate(layoutInflater)
    }

    override fun initData(intent: Intent) {}

    override fun initView() {
        binding.apply {
            // Back button
            ivBack.setOnClickListener { finish() }

            // RecyclerView
            rvMatches.apply {
                layoutManager = LinearLayoutManager(this@ActivityScoresFixtures)
                adapter = sectionAdapter
            }
        }
    }

    override fun initAction() {
        binding.groupSelectorLayout.setOnClickListener { showGroupSelectionDialog() }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.selectedFilter.collect { filter ->
                filter?.let { binding.tvSelectedGroup.text = it }
            }
        }

        lifecycleScope.launch {
            viewModel.sections.collect { sections -> sectionAdapter.submitList(sections) }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun showGroupSelectionDialog() {
        val filters = viewModel.filters.value
        if (filters.isEmpty()) return

        val currentFilter = viewModel.selectedFilter.value

        AlertDialog.Builder(this)
            .setTitle("Select Round")
            .setSingleChoiceItems(filters.toTypedArray(), filters.indexOf(currentFilter)) { dialog, which ->
                viewModel.selectFilter(filters[which])
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
