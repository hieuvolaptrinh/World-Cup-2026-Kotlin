package com.worldcup.app.domain.repository

import com.worldcup.app.data.remote.dto.GroupDto
import com.worldcup.app.data.remote.dto.MatchDto
import com.worldcup.app.data.remote.dto.TeamInfoDto

interface WorldCupRepository {
    suspend fun getAllMatches(): List<MatchDto>
    suspend fun getMatchesByGroup(group: String): List<MatchDto>
    suspend fun getAllTeams(): List<TeamInfoDto>
    suspend fun getTeamByCode(code: String): TeamInfoDto?
    suspend fun getAllGroups(): List<GroupDto>
}
