package com.example.newsapp.domain.repo

import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.NetworkResult

interface NewsRepository {
    suspend fun getNews() : NetworkResult<List<Article>>
}