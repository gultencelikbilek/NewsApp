package com.example.newsapp.data.usecase

import com.example.newsapp.data.di.FavoriteNewsRepositoryImpl
import com.example.newsapp.domain.model.ArticleData
import javax.inject.Inject

class DeleteNewsUseCase @Inject constructor(private val repositoryImpl: FavoriteNewsRepositoryImpl) {

    operator suspend fun invoke(articleData: ArticleData) = repositoryImpl.deleteNews(articleData)
}