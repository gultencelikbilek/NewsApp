package com.example.newsapp.data.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.newsapp.data.network.ApiService
import com.example.newsapp.data.Constants
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repo.NewsRepository
import com.example.newsapp.presentation.NetworkResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val apiService: ApiService) : NewsRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(): String {
        val previousDate = LocalDate.now().minusDays(1)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return previousDate.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getNews(): NetworkResult<List<Article>> {
        return try {
            val currentDate = getCurrentDate()
            val response = apiService.getNews("apple", currentDate, currentDate, "popularity", Constants.API_KEY)
            if (response.isSuccessful) {
                NetworkResult.SuccessNews(response.body()!!.articles)
            } else {
                NetworkResult.Failure("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Failure("Exception: ${e.message}")
        }
    }
}
