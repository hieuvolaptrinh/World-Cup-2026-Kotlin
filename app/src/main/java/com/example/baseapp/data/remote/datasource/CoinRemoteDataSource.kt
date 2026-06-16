package com.worldcup.app.data.remote.datasource

import com.worldcup.app.data.remote.Resource
import com.worldcup.app.data.remote.dto.Coin
import com.worldcup.app.data.remote.dto.IdentifyRequest

interface CoinRemoteDataSource {

    suspend fun getCoinById(id: String): Resource<Coin>

    suspend fun identifyCoin(request: IdentifyRequest): Resource<Coin>

    suspend fun getTop20Coin(): Resource<List<Coin>>
}