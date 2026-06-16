package com.worldcup.app.data.remote.datasource

import com.worldcup.app.data.remote.dto.SquadResponse
import com.worldcup.app.data.remote.dto.StadiumsResponse
import com.worldcup.app.data.remote.dto.TeamInfoDto
import com.worldcup.app.data.remote.dto.WorldCupGroupsResponse
import com.worldcup.app.data.remote.dto.WorldCupMatchesResponse
import retrofit2.Response

interface WorldCupRemoteDataSource {

    suspend fun getAllMatches(): Response<WorldCupMatchesResponse>


    suspend fun getGroups(): Response<WorldCupGroupsResponse>


    suspend fun getSquads(): Response<List<SquadResponse>>


    suspend fun getStadiums(): Response<StadiumsResponse>


    suspend fun getTeams(): Response<List<TeamInfoDto>>
}