package com.example.baseapp.domain.usecase.worldcup.container

import com.example.baseapp.domain.usecase.worldcup.*

data class WorldCupUseCases(
        val getAllMatches: GetAllMatchesUseCase,
        val getMatchesByGroup: GetMatchesByGroupUseCase,
        val getAllGroups: GetAllGroupsUseCase,
        val getAllTeams: GetAllTeamsUseCase
)
