package com.example.primohomepage.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feeds")
data class PrimoFeedEntity(
    @PrimaryKey val uuid: String,
    val feedInfo: String?,
    val articleList: String?
)