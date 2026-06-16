package com.worldcup.app.di

import com.worldcup.app.domain.usecase.coin.GetCoinByIdUseCase
import com.worldcup.app.domain.usecase.coin.GetTop20CoinUseCase
import com.worldcup.app.domain.usecase.coin.container.CoinUseCases
import com.worldcup.app.domain.usecase.login.GetAllLoginsUseCase
import com.worldcup.app.domain.usecase.login.GetLoginByNameUseCase
import com.worldcup.app.domain.usecase.login.container.LoginUseCases
import com.worldcup.app.domain.usecase.worldcup.*
import com.worldcup.app.domain.usecase.worldcup.container.WorldCupUseCases
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
        return CoinUseCases(getTop20Coin = getTop20CoinUseCase, getCoinById = getCoinByIdUseCase)
    }

    @Provides
    fun provideLoginUseCases(
            getAll: GetAllLoginsUseCase,
            getByName: GetLoginByNameUseCase
    ): LoginUseCases {
        return LoginUseCases(getAll, getByName)
    }

    @Provides
    fun provideWorldCupUseCases(
            getAllMatches: GetAllMatchesUseCase,
            getMatchesByGroup: GetMatchesByGroupUseCase,
            getAllGroups: GetAllGroupsUseCase,
            getAllTeams: GetAllTeamsUseCase
    ): WorldCupUseCases {
        return WorldCupUseCases(
                getAllMatches,
                getMatchesByGroup,
                getAllGroups,
                getAllTeams
        )
    }
}
