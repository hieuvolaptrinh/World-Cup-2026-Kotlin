package com.worldcup.app.ui.page.standings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.worldcup.app.databinding.ItemStandingBinding
import com.worldcup.app.ui.page.standings.StandingUiModel

class StandingSimpleAdapter :
        ListAdapter<StandingUiModel, StandingSimpleAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
                ItemStandingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemStandingBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(standing: StandingUiModel) {
            binding.apply {
                tvPosition.text = standing.position.toString()
                tvTeamName.text = standing.teamName
                tvPlayed.text = standing.played.toString()
                tvWon.text = standing.won.toString()
                tvDrawn.text = standing.drawn.toString()
                tvLost.text = standing.lost.toString()
                tvPoints.text = standing.points.toString()

                if (standing.teamFlag.isNotEmpty()) {
                    Glide.with(root.context).load(standing.teamFlag).into(ivTeamFlag)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<StandingUiModel>() {
        override fun areItemsTheSame(oldItem: StandingUiModel, newItem: StandingUiModel): Boolean {
            return oldItem.teamName == newItem.teamName
        }

        override fun areContentsTheSame(
                oldItem: StandingUiModel,
                newItem: StandingUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
