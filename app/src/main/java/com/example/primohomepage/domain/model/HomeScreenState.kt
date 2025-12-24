package com.example.primohomepage.domain.model

data class HomeScreenState(
    val articleModels: List<ArticleModel> = listOf(),
    val primoInfo: PrimoInfoModel? = null
)