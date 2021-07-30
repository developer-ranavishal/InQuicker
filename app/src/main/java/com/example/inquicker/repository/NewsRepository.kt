package com.example.inquicker.repository

import androidx.lifecycle.LiveData
import com.example.inquicker.database.ArticleDao
import com.example.inquicker.database.ArticleDatabase
import com.example.inquicker.model.Article
import com.example.inquicker.model.NewsResponse
import com.example.inquicker.network.NewsService
import retrofit2.Response

/*
 we create a repository to manage the offline cache,
our Room database doesn't have logic for managing the offline cache,
it only has methods to insert and retrieve the data.
 The repository will have the logic to fetch the network results and to keep the database up-to-date.
 */
class NewsRepository(val database: ArticleDatabase) {

    private val articleDao = database.ArticleDao()
    val getAllArticle: LiveData<List<Article>> = articleDao.getAllArticles()

    suspend fun getBreakingNews(country: String, page: Int) =
        NewsService.retrofitService.getHeadLines(country, page)

    suspend fun getSearchNews(searchType: String, page: Int) =
        NewsService.retrofitService.getEverything(searchType, page)

    suspend fun upsertArticle(article: Article) =
        articleDao.upsertArticle(article)


    suspend fun deleteArticle(article: Article) =
        articleDao.deleteArticle(article)



}