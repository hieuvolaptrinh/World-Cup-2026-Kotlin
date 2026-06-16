package com.worldcup.app.data.remote.datasource

import com.worldcup.app.data.remote.api.APIWorldCup

class WorldCupRemoteDataSourceImpl(
    private val api: APIWorldCup
) : WorldCupRemoteDataSource {

    override suspend fun getAllMatches() = api.getAllMatches()

    override suspend fun getGroups() = api.getGroups()

    override suspend fun getSquads() = api.getSquads()

    override suspend fun getStadiums() = api.getStadiums()

    override suspend fun getTeams() = api.getTeams()
}