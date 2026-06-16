package com.example.baseapp.ui.model

data class MatchUiModel(
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
        val stadium: String
)
