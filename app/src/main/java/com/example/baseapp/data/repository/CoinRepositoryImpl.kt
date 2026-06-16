package com.worldcup.app.data.repository

import com.worldcup.app.data.remote.datasource.CoinRemoteDataSource
import com.worldcup.app.data.remote.dto.IdentifyRequest
import com.worldcup.app.domain.repository.CoinRepository

class CoinRepositoryImpl(
    private val remote: CoinRemoteDataSource
) : CoinRepository {

    override suspend fun getCoinById(id: String) =
        remote.getCoinById(id)

    override suspend fun identifyCoin(request: IdentifyRequest) =
        remote.identifyCoin(request)

    override suspend fun getTop20Coin() =
        remote.getTop20Coin()
}