package com.example.baseapp.domain.usecase.coin.container

import com.example.baseapp.domain.usecase.coin.GetCoinByIdUseCase
import com.example.baseapp.domain.usecase.coin.GetTop20CoinUseCase

data class CoinUseCases(
    val getTop20Coin: GetTop20CoinUseCase,
    val getCoinById: GetCoinByIdUseCase
)