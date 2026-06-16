package com.worldcup.app.data.remote.datasource

import com.worldcup.app.data.remote.api.APICoin
import com.worldcup.app.data.remote.apiCall
import com.worldcup.app.data.remote.dto.IdentifyRequest

class CoinRemoteDataSourceImpl(
    private val api: APICoin
) : CoinRemoteDataSource {

    override suspend fun getCoinById(id: String) =
        apiCall { api.getCoinById(id) }

    override suspend fun identifyCoin(request: IdentifyRequest) =
        apiCall { api.identifyCoin(request) }

    override suspend fun getTop20Coin() =
        apiCall { api.getTop20Coin() }
}