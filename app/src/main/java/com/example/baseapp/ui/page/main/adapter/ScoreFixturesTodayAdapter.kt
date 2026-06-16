package com.worldcup.app.ui.page.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldcup.app.databinding.ItemMatchTodayBinding
import com.worldcup.app.ui.model.MatchTodayUIModel
import com.worldcup.app.utils.decodeUnicodeFlag

class ScoreFixturesTodayAdapter :
        RecyclerView.Adapter<ScoreFixturesTodayAdapter.ScoreFixturesHolder>() {

    private val matches = mutableListOf<MatchTodayUIModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreFixturesHolder {
        val binding =
                ItemMatchTodayBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        return ScoreFixturesHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreFixturesHolder, position: Int) {
        holder.bind(matches[position])
    }

    override fun getItemCount(): Int = matches.size

    fun updateMatches(newMatches: List<MatchTodayUIModel>) {
        matches.clear()
        matches.addAll(newMatches)
        notifyDataSetChanged()
    }

    class ScoreFixturesHolder(private val binding: ItemMatchTodayBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(match: MatchTodayUIModel) {
            binding.apply {
                tvDate.text = match.matchDate
                tvRound.text = match.round.orEmpty()
                tvTime.text = match.matchTime

                tvHomeTeam.text = match.homeTeamName
                ivHomeFlag.text = match.homeTeamFlag.decodeUnicodeFlag()

                tvAwayTeam.text = match.awayTeamName
                ivAwayFlag.text = match.awayTeamFlag.decodeUnicodeFlag()

                tvHomeScore.text = match.homeScore
                tvAwayScore.text = match.awayScore

                tvStatus.text = if (match.status == "SCHEDULED") "" else match.status
            }
        }
    }
}
