package com.example.inquicker.database

import android.content.Context
import androidx.room.*
import com.example.inquicker.converter.Convertors
import com.example.inquicker.model.Article

@Database(entities =[Article::class],version = 1,exportSchema = false)
@TypeConverters(Convertors::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun ArticleDao(): ArticleDao   // dao reference return

    // singleton room database
    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null
        fun getArticleDatabase(context: Context): ArticleDatabase {
            return INSTANCE ?: synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "user_database"
                )
                    .build()
                INSTANCE = tempInstance
                tempInstance
            }
        }
    }

}
