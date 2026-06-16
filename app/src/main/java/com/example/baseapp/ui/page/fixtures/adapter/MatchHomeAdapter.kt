package com.example.baseapp.ui.page.fixtures.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.baseapp.databinding.ItemMatchHomeBinding
import com.example.baseapp.ui.model.MatchUiModel

class MatchHomeAdapter : ListAdapter<MatchUiModel, MatchHomeAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
                ItemMatchHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemMatchHomeBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(match: MatchUiModel) {
            binding.apply {
                // Date and time
                tvDate.text = match.matchDate
                tvTime.text = match.matchTime

                // Home team
                tvHomeTeam.text = match.homeTeamName
                if (match.homeTeamFlag.isNotEmpty()) {
                    Glide.with(root.context).load(match.homeTeamFlag).into(ivHomeFlag)
                }

                // Away team
                tvAwayTeam.text = match.awayTeamName
                if (match.awayTeamFlag.isNotEmpty()) {
                    Glide.with(root.context).load(match.awayTeamFlag).into(ivAwayFlag)
                }

                // Scores
                tvHomeScore.text = match.homeScore
                tvAwayScore.text = match.awayScore

                // Status
                if (match.status == "FT") {
                    tvStatus.text = "FT"
                } else if (match.status == "LIVE") {
                    tvStatus.text = "LIVE"
                }

                // Stadium
                tvStadium.text = match.stadium
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MatchUiModel>() {
        override fun areItemsTheSame(oldItem: MatchUiModel, newItem: MatchUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MatchUiModel, newItem: MatchUiModel): Boolean {
            return oldItem == newItem
        }
    }
}
