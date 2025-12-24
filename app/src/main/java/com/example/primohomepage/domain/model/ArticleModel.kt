package com.example.primohomepage.domain.model

import java.util.UUID

data class ArticleModel(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val dateString: String, //todo change it into other format
    val thumbnail: String,
    val descriptionHtmlText: String,
    val categories: List<String>
)