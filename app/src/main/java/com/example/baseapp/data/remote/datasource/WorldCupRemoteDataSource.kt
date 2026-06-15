package com.example.baseapp.data.remote.datasource

import com.example.baseapp.data.remote.dto.SquadResponse
import com.example.baseapp.data.remote.dto.StadiumsResponse
import com.example.baseapp.data.remote.dto.TeamInfoDto
import com.example.baseapp.data.remote.dto.WorldCupGroupsResponse
import com.example.baseapp.data.remote.dto.WorldCupMatchesResponse
import retrofit2.Response

interface WorldCupRemoteDataSource {

    suspend fun getAllMatches(): Response<WorldCupMatchesResponse>


    suspend fun getGroups(): Response<WorldCupGroupsResponse>


    suspend fun getSquads(): Response<List<SquadResponse>>


    suspend fun getStadiums(): Response<StadiumsResponse>


    suspend fun getTeams(): Response<List<TeamInfoDto>>
}