package com.example.baseapp.data.repository

import com.example.baseapp.data.remote.api.APIWorldCup
import com.example.baseapp.data.remote.dto.GroupDto
import com.example.baseapp.data.remote.dto.MatchDto
import com.example.baseapp.data.remote.dto.TeamInfoDto
import com.example.baseapp.domain.repository.WorldCupRepository
import javax.inject.Inject

class WorldCupRepositoryImpl @Inject constructor(private val api: APIWorldCup) :
        WorldCupRepository {

    private var cachedMatches: List<MatchDto>? = null
    private var cachedTeams: List<TeamInfoDto>? = null
    private var cachedGroups: List<GroupDto>? = null

    override suspend fun getAllMatches(): List<MatchDto> {
        if (cachedMatches == null) {
            val response = api.getAllMatches()
            if (response.isSuccessful) {
                cachedMatches = response.body()?.matches ?: emptyList()
            }
        }
        return cachedMatches ?: emptyList()
    }

    override suspend fun getMatchesByGroup(group: String): List<MatchDto> {
        val allMatches = getAllMatches()
        return allMatches.filter { it.group == group }
    }

    override suspend fun getAllTeams(): List<TeamInfoDto> {
        if (cachedTeams == null) {
            val response = api.getTeams()
            if (response.isSuccessful) {
                cachedTeams = response.body() ?: emptyList()
            }
        }
        return cachedTeams ?: emptyList()
    }

    override suspend fun getTeamByCode(code: String): TeamInfoDto? {
        val allTeams = getAllTeams()
        return allTeams.find { it.fifaCode == code }
    }

    override suspend fun getAllGroups(): List<GroupDto> {
        if (cachedGroups == null) {
            val response = api.getGroups()
            if (response.isSuccessful) {
                cachedGroups = response.body()?.groups ?: emptyList()
            }
        }
        return cachedGroups ?: emptyList()
    }
}
