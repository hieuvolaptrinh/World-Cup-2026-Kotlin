package com.example.baseapp.di

import com.example.baseapp.data.remote.api.APICoin
import com.example.baseapp.data.remote.datasource.CoinRemoteDataSource
import com.example.baseapp.data.remote.datasource.CoinRemoteDataSourceImpl
import com.example.baseapp.data.repository.CoinRepositoryImpl
import com.example.baseapp.data.repository.WorldCupRepositoryImpl
import com.example.baseapp.domain.repository.CoinRepository
import com.example.baseapp.domain.repository.WorldCupRepository
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
            api: com.example.baseapp.data.remote.api.APIWorldCup
    ): WorldCupRepository {
        return WorldCupRepositoryImpl(api)
    }
}
