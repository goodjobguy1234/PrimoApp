package com.example.primohomepage.domain.model

import java.util.UUID

data class ArticleModel(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val dateString: String,
    val thumbnail: String,
    val descriptionHtmlText: String,
    val categories: List<String>,
    val detailLink: String
)