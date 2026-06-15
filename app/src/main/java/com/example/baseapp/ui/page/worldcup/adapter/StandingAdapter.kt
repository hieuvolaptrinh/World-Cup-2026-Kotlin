package com.example.baseapp.ui.page.worldcup.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.baseapp.databinding.ItemStandingBinding
import com.example.baseapp.ui.page.worldcup.model.StandingWithTeam

class StandingAdapter :
        ListAdapter<StandingWithTeam, StandingAdapter.StandingViewHolder>(StandingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingViewHolder {
        val binding =
                ItemStandingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StandingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class StandingViewHolder(private val binding: ItemStandingBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(standingWithTeam: StandingWithTeam) {
            binding.apply {
                val standing = standingWithTeam.standing
                val team = standingWithTeam.team

                // Position indicator color based on position
                val color =
                        when (standing.position) {
                            1 -> Color.parseColor("#EC6613") // Orange
                            2 -> Color.parseColor("#EE9862") // Light orange
                            3 -> Color.parseColor("#F5C551") // Yellow
                            else -> Color.parseColor("#CCCCCC") // Gray
                        }
                viewPositionIndicator.setBackgroundColor(color)

                // Position number
                tvPosition.text = standing.position.toString()

                // Team flag and name
                Glide.with(root.context).load(team.flagUrl).into(ivFlag)
                tvTeamName.text = team.name

                // Statistics
                tvMatchesPlayed.text = standing.matchesPlayed.toString()
                tvWins.text = standing.wins.toString()
                tvDraws.text = standing.draws.toString()
                tvLosses.text = standing.losses.toString()
                tvGoalsFor.text = standing.goalsFor.toString()
                tvGoalsAgainst.text = standing.goalsAgainst.toString()
                tvPoints.text = standing.points.toString()
            }
        }
    }

    class StandingDiffCallback : DiffUtil.ItemCallback<StandingWithTeam>() {
        override fun areItemsTheSame(
                oldItem: StandingWithTeam,
                newItem: StandingWithTeam
        ): Boolean {
            return oldItem.standing.id == newItem.standing.id
        }

        override fun areContentsTheSame(
                oldItem: StandingWithTeam,
                newItem: StandingWithTeam
        ): Boolean {
            return oldItem == newItem
        }
    }
}
