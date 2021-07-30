package com.example.inquicker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.inquicker.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertArticle(article: Article) : Long

    @Query("SELECT * From  article")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
   suspend fun deleteArticle(article: Article)


}