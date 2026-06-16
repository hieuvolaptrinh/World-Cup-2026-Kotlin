package com.worldcup.app.data.remote.api

import com.worldcup.app.data.remote.dto.SquadResponse
import com.worldcup.app.data.remote.dto.StadiumsResponse
import com.worldcup.app.data.remote.dto.TeamInfoDto
import com.worldcup.app.data.remote.dto.WorldCupGroupsResponse
import com.worldcup.app.data.remote.dto.WorldCupMatchesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIWorldCup {

    @GET("worldcup.json")
    suspend fun getAllMatches(
        @Query("_") noCache: Long = System.currentTimeMillis()
    ): Response<WorldCupMatchesResponse>

    @GET("worldcup.groups.json")
    suspend fun getGroups(
        @Query("_") noCache: Long = System.currentTimeMillis()
    ): Response<WorldCupGroupsResponse>

    @GET("worldcup.squads.json")
    suspend fun getSquads(
        @Query("_") noCache: Long = System.currentTimeMillis()
    ): Response<List<SquadResponse>>

    @GET("worldcup.stadiums.json")
    suspend fun getStadiums(
        @Query("_") noCache: Long = System.currentTimeMillis()
    ): Response<StadiumsResponse>

    @GET("worldcup.teams.json")
    suspend fun getTeams(
        @Query("_") noCache: Long = System.currentTimeMillis()
    ): Response<List<TeamInfoDto>>
}
