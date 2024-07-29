package com.example.newsapp.domain.repo

import com.example.newsapp.domain.model.ArticleData

interface FavoriteNewsRepository {
    suspend fun addNews(articledata: ArticleData)

    suspend fun deleteNews(articledata: ArticleData)

    suspend fun getFavNewsList() : List<ArticleData>
}