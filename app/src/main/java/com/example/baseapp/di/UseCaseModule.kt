package com.example.baseapp.di

import com.example.baseapp.domain.usecase.coin.GetCoinByIdUseCase
import com.example.baseapp.domain.usecase.coin.GetTop20CoinUseCase
import com.example.baseapp.domain.usecase.coin.container.CoinUseCases
import com.example.baseapp.domain.usecase.login.GetAllLoginsUseCase
import com.example.baseapp.domain.usecase.login.GetLoginByNameUseCase
import com.example.baseapp.domain.usecase.login.container.LoginUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideCoinUseCases(
        getTop20CoinUseCase: GetTop20CoinUseCase,
        getCoinByIdUseCase: GetCoinByIdUseCase
    ): CoinUseCases {
        return CoinUseCases(
            getTop20Coin = getTop20CoinUseCase,
            getCoinById = getCoinByIdUseCase
        )
    }

    @Provides
    fun provideLoginUseCases(
        getAll: GetAllLoginsUseCase,
        getByName: GetLoginByNameUseCase
    ): LoginUseCases {
        return LoginUseCases(
            getAll,
            getByName
        )
    }
}