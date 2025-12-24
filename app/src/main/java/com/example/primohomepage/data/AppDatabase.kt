package com.example.primohomepage.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.primohomepage.data.entity.PrimoFeedEntity

@Database(entities = [PrimoFeedEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun primoFeedDao(): PrimoFeedDao
}