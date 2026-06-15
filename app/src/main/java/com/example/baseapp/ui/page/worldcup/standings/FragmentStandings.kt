package com.example.baseapp.ui.page.worldcup.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseapp.databinding.FragmentStandingsBinding
import com.example.baseapp.ui.base.BaseFragment
import com.example.baseapp.ui.page.worldcup.adapter.StandingAdapter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentStandings : BaseFragment<FragmentStandingsBinding>() {

    private val viewModel: StandingsViewModel by viewModels()
    private val standingAdapter = StandingAdapter()

    override fun getBindingInflater(
            inflater: LayoutInflater,
            container: ViewGroup?,
            attachToParent: Boolean
    ): FragmentStandingsBinding {
        return FragmentStandingsBinding.inflate(inflater, container, false)
    }

    override fun initData(bundle: Bundle?) {}

    override fun initViews() {
        binding.apply {
            rvStandings.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = standingAdapter
            }
        }
    }

    override fun initActions() {}

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.groups.collect { groups ->
                binding.chipGroupGroups.removeAllViews()
                groups.forEach { group ->
                    val chip =
                            Chip(requireContext()).apply {
                                text = "Group $group"
                                isCheckable = true
                                setOnClickListener { viewModel.selectGroup(group) }
                            }
                    binding.chipGroupGroups.addView(chip)
                }

                // Select first group by default
                if (binding.chipGroupGroups.childCount > 0) {
                    (binding.chipGroupGroups.getChildAt(0) as Chip).isChecked = true
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.standings.collect { standings -> standingAdapter.submitList(standings) }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedGroup.collect { group ->
                binding.tvSelectedGroup.text = "Group $group Standings"
            }
        }
    }
}
