package com.example.inquicker.model

import com.example.inquicker.model.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)