package com.worldcup.app.domain.usecase.worldcup.container

import com.worldcup.app.domain.usecase.worldcup.*

data class WorldCupUseCases(
        val getAllMatches: GetAllMatchesUseCase,
        val getMatchesByGroup: GetMatchesByGroupUseCase,
        val getAllGroups: GetAllGroupsUseCase,
        val getAllTeams: GetAllTeamsUseCase
)
