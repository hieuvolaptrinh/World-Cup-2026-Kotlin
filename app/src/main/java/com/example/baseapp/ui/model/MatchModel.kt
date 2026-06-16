package com.example.baseapp.ui.model

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
