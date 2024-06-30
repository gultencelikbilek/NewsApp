package com.example.newsapp.data.network

import com.example.newsapp.data.Constants
import com.example.newsapp.domain.model.NewsResponse
import com.example.newsapp.presentation.NetworkResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getNews(
        @Query("q") q:String,
        @Query("from") from : String,
        @Query("to") to : String,
        @Query("sortBy") sortBy:String,
        @Query("apiKey") apiKey : String
    ) : Response<NewsResponse>
}