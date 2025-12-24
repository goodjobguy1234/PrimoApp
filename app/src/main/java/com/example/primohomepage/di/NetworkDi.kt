package com.example.primohomepage.di

import com.example.primohomepage.data.PrimoFeedsRemoteDatasource
import com.example.primohomepage.data.repository.PrimoFeedsRepositoryImpl
import com.example.primohomepage.domain.ArticleFeedsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDi {
    @Provides
    fun providePrimoFeedsRepository(
        datasource: PrimoFeedsRemoteDatasource
    ): ArticleFeedsRepository {
        return PrimoFeedsRepositoryImpl(datasource)
    }
}