package com.worldcup.app.ui.page.standings

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.worldcup.app.databinding.ActivityStandingsBinding
import com.worldcup.app.ui.base.BaseActivity
import com.worldcup.app.ui.page.standings.adapter.StandingSimpleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityStandings : BaseActivity<ActivityStandingsBinding>() {

    private val viewModel: StandingsViewModel by viewModels()
    private val standingAdapter = StandingSimpleAdapter()

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityStandingsBinding {
        return ActivityStandingsBinding.inflate(layoutInflater)
    }

    override fun initData(intent: Intent) {}

    override fun initView() {
        binding.apply {
            // Back button
            ivBack.setOnClickListener { finish() }

            // RecyclerView
            rvStandings.apply {
                layoutManager = LinearLayoutManager(this@ActivityStandings)
                adapter = standingAdapter
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
            viewModel.standings.collect { standings -> standingAdapter.submitList(standings) }
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
