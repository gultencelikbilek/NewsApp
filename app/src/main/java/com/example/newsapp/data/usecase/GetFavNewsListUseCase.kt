package com.example.newsapp.data.usecase

import com.example.newsapp.data.di.FavoriteNewsRepositoryImpl
import com.example.newsapp.domain.model.ArticleData
import javax.inject.Inject

class GetFavNewsListUseCase @Inject constructor(private val repositoryImpl: FavoriteNewsRepositoryImpl)  {
    operator suspend fun invoke() : List<ArticleData> = repositoryImpl.getFavNewsList()
}