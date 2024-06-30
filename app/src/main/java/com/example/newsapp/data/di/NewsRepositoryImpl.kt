package com.example.newsapp.data.di

import com.example.newsapp.data.network.ApiService
import com.example.newsapp.data.Constants
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repo.NewsRepository
import com.example.newsapp.presentation.NetworkResult
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val apiService: ApiService) : NewsRepository {
    override suspend fun getNews(): NetworkResult<List<Article>> {
        return try {
            val response = apiService.getNews("apple", "2024-06-29", "2024-06-29", "popularity", Constants.API_KEY)
            if (response.isSuccessful) {
                NetworkResult.SuccessNews(response.body()!!.articles )
            } else {
                NetworkResult.Failure("Error: ${response.message()}")
            }
        }catch (e:Exception){
            NetworkResult.Failure("Exception: ${e.message}")

        }
    }

}