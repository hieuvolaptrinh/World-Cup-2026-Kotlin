package com.worldcup.app.di

import com.worldcup.app.data.remote.api.APICoin
import com.worldcup.app.data.remote.datasource.CoinRemoteDataSource
import com.worldcup.app.data.remote.datasource.CoinRemoteDataSourceImpl
import com.worldcup.app.data.repository.CoinRepositoryImpl
import com.worldcup.app.data.repository.WorldCupRepositoryImpl
import com.worldcup.app.domain.repository.CoinRepository
import com.worldcup.app.domain.repository.WorldCupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCoinRemoteDataSource(api: APICoin): CoinRemoteDataSource {
        return CoinRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(remote: CoinRemoteDataSource): CoinRepository {
        return CoinRepositoryImpl(remote)
    }

    @Provides
    @Singleton
    fun provideWorldCupRepository(
            api: com.worldcup.app.data.remote.api.APIWorldCup
    ): WorldCupRepository {
        return WorldCupRepositoryImpl(api)
    }
}
