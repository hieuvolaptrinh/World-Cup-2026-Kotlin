package com.worldcup.app.ui.model

import com.worldcup.app.data.local.entity.WorldCupMatchEntity
import com.worldcup.app.utils.toDisplayTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MatchUIModel(
        val id: String,
        val homeTeamName: String,
        val homeTeamFlag: String,
        val awayTeamName: String,
        val awayTeamFlag: String,
        val homeScore: String,
        val awayScore: String,
        val matchDate: String,
        val matchTime: String,
        val status: String, // "FT", "LIVE", "SCHEDULED"
        val stadium: String?
)

data class MatchSectionUIModel(
        val title: String,
        val matches: List<MatchUIModel>
)
data class MatchTodayUIModel(
        val id: String,
        val homeTeamName: String,
        val homeTeamFlag: String,
        val awayTeamName: String,
        val awayTeamFlag: String,
        val homeScore: String,
        val awayScore: String,
        val matchDate: String,
        val matchTime: String,
        val status: String, // "FT", "LIVE", "SCHEDULED"
        val round: String?
)

fun WorldCupMatchEntity.toUIModel(): MatchUIModel {
        val isFinished = homeScore != null && awayScore != null

        return MatchUIModel(
                id = id,
                homeTeamName = homeTeamName,
                homeTeamFlag = homeTeamFlag,
                awayTeamName = awayTeamName,
                awayTeamFlag = awayTeamFlag,
                homeScore = homeScore?.toString() ?: "-",
                awayScore = awayScore?.toString() ?: "-",
                matchDate = localMatchDate ?: date,
                matchTime = kickoffMillis?.toDisplayTime() ?: (time ?: ""),
                status = if (isFinished) "FT" else "SCHEDULED",
                stadium = stadium
        )
}
