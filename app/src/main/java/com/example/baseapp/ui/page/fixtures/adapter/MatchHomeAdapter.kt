package com.example.baseapp.ui.page.fixtures.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.databinding.ItemMatchBinding
import com.example.baseapp.ui.model.MatchUIModel
import com.example.baseapp.utils.decodeUnicodeFlag

class MatchHomeAdapter : ListAdapter<MatchUIModel, MatchHomeAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(match: MatchUIModel) {
            binding.apply {
                // Date and time
                tvDate.text = match.matchDate
                tvTime.text = match.matchTime

                // Home team
                tvHomeTeam.text = match.homeTeamName
                ivHomeFlag.text = match.homeTeamFlag.decodeUnicodeFlag()

                // Away team
                tvAwayTeam.text = match.awayTeamName
                ivAwayFlag.text = match.awayTeamFlag.decodeUnicodeFlag()

                // Scores
                tvHomeScore.text = match.homeScore
                tvAwayScore.text = match.awayScore

                // Status
                tvStatus.text = if (match.status == "SCHEDULED") "" else match.status
            }
        }


    }

    class DiffCallback : DiffUtil.ItemCallback<MatchUIModel>() {
        override fun areItemsTheSame(oldItem: MatchUIModel, newItem: MatchUIModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MatchUIModel, newItem: MatchUIModel): Boolean {
            return oldItem == newItem
        }
    }
}
