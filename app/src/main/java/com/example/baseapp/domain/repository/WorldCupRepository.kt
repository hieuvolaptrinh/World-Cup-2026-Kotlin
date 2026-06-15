package com.example.baseapp.domain.repository

import com.example.baseapp.data.local.entity.Match
import com.example.baseapp.data.local.entity.MatchStatus
import com.example.baseapp.data.local.entity.Standing
import com.example.baseapp.data.local.entity.Team
import kotlinx.coroutines.flow.Flow

interface WorldCupRepository {
    // Teams
    fun getAllTeams(): Flow<List<Team>>
    suspend fun getTeamById(teamId: String): Team?
    suspend fun insertTeams(teams: List<Team>)

    // Matches
    fun getAllMatches(): Flow<List<Match>>
    fun getMatchesByRound(round: String): Flow<List<Match>>
    fun getMatchesByStatus(status: MatchStatus): Flow<List<Match>>
    suspend fun getAllRounds(): List<String>
    suspend fun insertMatches(matches: List<Match>)

    // Standings
    fun getStandingsByGroup(groupName: String): Flow<List<Standing>>
    suspend fun getAllGroups(): List<String>
    fun getAllStandings(): Flow<List<Standing>>
    suspend fun insertStandings(standings: List<Standing>)

    // Initialize sample data
    suspend fun initializeSampleData()
}
