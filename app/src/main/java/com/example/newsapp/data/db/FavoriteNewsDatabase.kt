package com.example.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.domain.model.ArticleData

@Database(entities = [ArticleData::class], version = 4)
abstract class FavoriteNewsDatabase : RoomDatabase(){

    abstract fun newsDao() : FavoriteNewsDao
}