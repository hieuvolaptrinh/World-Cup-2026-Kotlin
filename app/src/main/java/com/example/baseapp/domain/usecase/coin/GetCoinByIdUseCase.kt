package com.example.baseapp.domain.usecase.coin

import com.example.baseapp.data.remote.Resource
import com.example.baseapp.data.remote.dto.Coin
import com.example.baseapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinByIdUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(id: String): Resource<Coin> {
        return repository.getCoinById(id)
    }
}