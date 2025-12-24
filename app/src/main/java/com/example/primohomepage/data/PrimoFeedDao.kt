package com.example.primohomepage.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.primohomepage.data.entity.PrimoFeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrimoFeedDao {
    @Query("SELECT * FROM feeds")
    fun getPrimoFeeds(): Flow<PrimoFeedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: PrimoFeedEntity)

    @Query("DELETE FROM feeds")
    suspend fun clearAll()

    @Transaction
    suspend fun clearAndInsert(feedModel: PrimoFeedEntity) {
        clearAll()
        insertArticles(feedModel)
    }
}