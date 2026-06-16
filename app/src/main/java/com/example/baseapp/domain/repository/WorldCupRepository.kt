package com.example.baseapp.domain.repository

import com.example.baseapp.data.remote.dto.GroupDto
import com.example.baseapp.data.remote.dto.MatchDto
import com.example.baseapp.data.remote.dto.TeamInfoDto

interface WorldCupRepository {
    suspend fun getAllMatches(): List<MatchDto>
    suspend fun getMatchesByGroup(group: String): List<MatchDto>
    suspend fun getAllTeams(): List<TeamInfoDto>
    suspend fun getTeamByCode(code: String): TeamInfoDto?
    suspend fun getAllGroups(): List<GroupDto>
}
