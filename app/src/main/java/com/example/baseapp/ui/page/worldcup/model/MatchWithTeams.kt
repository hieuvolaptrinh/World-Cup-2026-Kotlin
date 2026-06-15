package com.example.baseapp.ui.page.worldcup.model

import com.example.baseapp.data.local.entity.Match
import com.example.baseapp.data.local.entity.Team

data class MatchWithTeams(val match: Match, val homeTeam: Team, val awayTeam: Team)
