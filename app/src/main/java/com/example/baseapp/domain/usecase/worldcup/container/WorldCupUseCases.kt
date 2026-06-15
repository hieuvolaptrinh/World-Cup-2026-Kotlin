package com.example.baseapp.domain.usecase.worldcup.container

import com.example.baseapp.domain.usecase.worldcup.*

data class WorldCupUseCases(
        val getAllMatches: GetAllMatchesUseCase,
        val getMatchesByRound: GetMatchesByRoundUseCase,
        val getAllRounds: GetAllRoundsUseCase,
        val getStandingsByGroup: GetStandingsByGroupUseCase,
        val getAllGroups: GetAllGroupsUseCase,
        val getAllStandings: GetAllStandingsUseCase,
        val getTeamById: GetTeamByIdUseCase,
        val initializeSampleData: InitializeSampleDataUseCase
)
