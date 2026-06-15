package com.example.baseapp.data.remote.api

import com.example.baseapp.data.remote.dto.SquadResponse
import com.example.baseapp.data.remote.dto.StadiumsResponse
import com.example.baseapp.data.remote.dto.TeamInfoDto
import com.example.baseapp.data.remote.dto.WorldCupGroupsResponse
import com.example.baseapp.data.remote.dto.WorldCupMatchesResponse
import retrofit2.Response
import retrofit2.http.GET

interface APIWorldCup {

    @GET("worldcup.json")
    suspend fun getAllMatches(): Response<WorldCupMatchesResponse>

    @GET("worldcup.groups.json")
    suspend fun getGroups(): Response<WorldCupGroupsResponse>

    @GET("worldcup.squads.json")
    suspend fun getSquads(): Response<List<SquadResponse>>

    @GET("worldcup.stadiums.json")
    suspend fun getStadiums(): Response<StadiumsResponse>

    @GET("worldcup.teams.json")
    suspend fun getTeams(): Response<List<TeamInfoDto>>
}
