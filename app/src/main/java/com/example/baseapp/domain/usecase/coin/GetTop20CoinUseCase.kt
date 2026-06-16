package com.worldcup.app.domain.usecase.coin

import com.worldcup.app.data.remote.Resource
import com.worldcup.app.data.remote.dto.Coin
import com.worldcup.app.domain.repository.CoinRepository
import javax.inject.Inject

class GetTop20CoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(): Resource<List<Coin>> {
        return repository.getTop20Coin()
    }
}