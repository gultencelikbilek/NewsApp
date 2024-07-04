package com.example.newsapp.data.usecase

import com.example.newsapp.data.di.FavoriteNewsRepositoryImpl
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.ArticleData
import javax.inject.Inject

class AddNewsUseCase @Inject constructor(private val repositoryImpl: FavoriteNewsRepositoryImpl) {

    operator suspend fun invoke(articledata: ArticleData) {
        repositoryImpl.addNews(articledata)
    }
}