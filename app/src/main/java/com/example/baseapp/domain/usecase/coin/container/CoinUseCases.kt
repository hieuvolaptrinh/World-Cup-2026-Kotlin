package com.worldcup.app.domain.usecase.coin.container

import com.worldcup.app.domain.usecase.coin.GetCoinByIdUseCase
import com.worldcup.app.domain.usecase.coin.GetTop20CoinUseCase

data class CoinUseCases(
    val getTop20Coin: GetTop20CoinUseCase,
    val getCoinById: GetCoinByIdUseCase
)