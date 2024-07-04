package com.example.newsapp.data.di

import android.content.Context
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.ArticleData
import com.example.newsapp.domain.repo.FavoriteNewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FavoriteNewsRepositoryImpl @Inject constructor(@ApplicationContext val app : Context): FavoriteNewsRepository {
    override suspend fun addNews(articledata: ArticleData) {
        AppModule.providesDatabase(app).newsDao().addNews(articledata)
    }

    override suspend fun getFavNewsList(): List<ArticleData> {
      return AppModule.providesDatabase(app).newsDao().getFavNewsList()
    }

}