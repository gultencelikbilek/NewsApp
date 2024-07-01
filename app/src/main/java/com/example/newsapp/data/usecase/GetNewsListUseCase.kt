package com.example.newsapp.data.usecase

import com.example.newsapp.data.di.NewsRepositoryImpl
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsListUseCase @Inject constructor(private val repositoryImpl: NewsRepositoryImpl) {
    operator suspend fun invoke() : Flow<NetworkResult<List<Article>>> = flow{
        emit(NetworkResult.Loading)
        try {
            val result = repositoryImpl.getNews()
            emit(result)
        }catch (e:Exception){
            emit(NetworkResult.Failure("Exception: ${e.message}"))
        }
    }
}