package com.example.baseapp.ui.page.worldcup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.baseapp.data.local.entity.MatchStatus
import com.example.baseapp.databinding.ItemMatchBinding
import com.example.baseapp.ui.page.worldcup.model.MatchWithTeams
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter :
        ListAdapter<MatchWithTeams, MatchAdapter.MatchViewHolder>(MatchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MatchViewHolder(private val binding: ItemMatchBinding) :
            RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        fun bind(matchWithTeams: MatchWithTeams) {
            binding.apply {
                val match = matchWithTeams.match
                val homeTeam = matchWithTeams.homeTeam
                val awayTeam = matchWithTeams.awayTeam

                // Date and time
                val matchDate = Date(match.matchDateTime)
                tvDate.text = dateFormat.format(matchDate)
                tvTime.text = timeFormat.format(matchDate)

                // Home team
                tvHomeTeam.text = homeTeam.name
                Glide.with(root.context).load(homeTeam.flagUrl).into(ivHomeFlag)

                // Away team
                tvAwayTeam.text = awayTeam.name
                Glide.with(root.context).load(awayTeam.flagUrl).into(ivAwayFlag)

                // Scores and status
                when (match.status) {
                    MatchStatus.FINISHED -> {
                        tvHomeScore.text = match.homeScore?.toString() ?: "-"
                        tvAwayScore.text = match.awayScore?.toString() ?: "-"
                        tvStatus.text = "FT"
                        tvStatus.visibility = View.VISIBLE
                        tvHomeScore.visibility = View.VISIBLE
                        tvAwayScore.visibility = View.VISIBLE
                    }
                    MatchStatus.LIVE -> {
                        tvHomeScore.text = match.homeScore?.toString() ?: "0"
                        tvAwayScore.text = match.awayScore?.toString() ?: "0"
                        tvStatus.text = "LIVE"
                        tvStatus.visibility = View.VISIBLE
                        tvHomeScore.visibility = View.VISIBLE
                        tvAwayScore.visibility = View.VISIBLE
                    }
                    MatchStatus.SCHEDULED -> {
                        tvHomeScore.text = "-"
                        tvAwayScore.text = "-"
                        tvStatus.visibility = View.GONE
                        tvHomeScore.visibility = View.VISIBLE
                        tvAwayScore.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    class MatchDiffCallback : DiffUtil.ItemCallback<MatchWithTeams>() {
        override fun areItemsTheSame(oldItem: MatchWithTeams, newItem: MatchWithTeams): Boolean {
            return oldItem.match.id == newItem.match.id
        }

        override fun areContentsTheSame(oldItem: MatchWithTeams, newItem: MatchWithTeams): Boolean {
            return oldItem == newItem
        }
    }
}
