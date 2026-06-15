package com.example.baseapp.ui.page.worldcup.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseapp.databinding.FragmentWorldcupHomeBinding
import com.example.baseapp.ui.base.BaseFragment
import com.example.baseapp.ui.page.worldcup.adapter.MatchAdapter
import com.example.baseapp.ui.page.worldcup.adapter.StandingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentWorldCupHome : BaseFragment<FragmentWorldcupHomeBinding>() {

    private val viewModel: WorldCupHomeViewModel by viewModels()
    private val matchAdapter = MatchAdapter()
    private val standingAdapter = StandingAdapter()

    override fun getBindingInflater(
            inflater: LayoutInflater,
            container: ViewGroup?,
            attachToParent: Boolean
    ): FragmentWorldcupHomeBinding {
        return FragmentWorldcupHomeBinding.inflate(inflater, container, false)
    }

    override fun initData(bundle: Bundle?) {}

    override fun initViews() {
        binding.apply {
            rvRecentMatches.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = matchAdapter
            }

            rvGroupStandings.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = standingAdapter
            }
        }
    }

    override fun initActions() {}

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recentMatches.collect { matches -> matchAdapter.submitList(matches) }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.groupStandings.collect { groupStandings ->
                // Show first group standings
                val firstGroup = groupStandings.values.firstOrNull() ?: emptyList()
                standingAdapter.submitList(firstGroup)

                binding.tvGroupName.text = "Group ${groupStandings.keys.firstOrNull() ?: "A"}"
            }
        }
    }
}
