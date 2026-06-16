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
import com.example.baseapp.ui.page.fixtures.adapter.MatchHomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityScoresFixtures : BaseActivity<ActivityScoresFixturesBinding>() {

    private val viewModel: ScoresFixturesViewModel by viewModels()
    private val matchAdapter = MatchHomeAdapter()

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
                adapter = matchAdapter
            }
        }
    }

    override fun initAction() {
        binding.groupSelectorLayout.setOnClickListener { showGroupSelectionDialog() }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.selectedGroup.collect { group ->
                group?.let { binding.tvSelectedGroup.text = "${it.name}" }
            }
        }

        lifecycleScope.launch {
            viewModel.matches.collect { matches -> matchAdapter.submitList(matches) }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun showGroupSelectionDialog() {
        val groups = viewModel.groups.value
        if (groups.isEmpty()) return

        val groupNames = groups.map { it.name }.toTypedArray()
        val currentGroup = viewModel.selectedGroup.value

        AlertDialog.Builder(this)
            .setTitle("Select Group")
            .setSingleChoiceItems(groupNames, groups.indexOf(currentGroup)) { dialog, which ->
                viewModel.selectGroup(groups[which])
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
