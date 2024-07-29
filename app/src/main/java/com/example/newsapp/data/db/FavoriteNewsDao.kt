package com.example.newsapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.domain.model.ArticleData

@Dao
interface FavoriteNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(article: ArticleData)

    @Query("SELECT * FROM articledata")
    suspend fun getFavNewsList() : List<ArticleData>

    @Delete
    suspend fun deleteNews(article: ArticleData)
}