package com.example.primohomepage.di

import android.content.Context
import androidx.room.Room
import com.example.primohomepage.data.PrimoFeedsService
import com.example.primohomepage.data.AppDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        val BASE_URL = "https://api.rss2json.com/v1/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): PrimoFeedsService =
        retrofit.create(PrimoFeedsService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "primo_feeds_db")
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase) = db.primoFeedDao()
}