package com.example.primohomepage.di

import com.example.primohomepage.data.PrimoFeedsRemoteDatasource
import com.example.primohomepage.data.PrimoFeedDao
import com.example.primohomepage.data.repository.PrimoFeedsRepositoryImpl
import com.example.primohomepage.domain.ArticleFeedsRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkDi {
    @Provides
    fun providePrimoFeedsRepository(
        datasource: PrimoFeedsRemoteDatasource,
        localDatasource: PrimoFeedDao,
        moshi: Moshi
    ): ArticleFeedsRepository {
        return PrimoFeedsRepositoryImpl(
            remoteDatasource = datasource,
            localDatasource = localDatasource,
            moshi = moshi
        )
    }
}