package com.example.newsapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.network.ApiService
import com.example.newsapp.data.Constants
import com.example.newsapp.data.db.FavoriteNewsDatabase
import com.example.newsapp.domain.repo.FavoriteNewsRepository
import com.example.newsapp.domain.repo.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext app: Context): FavoriteNewsDatabase =
        Room.databaseBuilder(
            app,
            FavoriteNewsDatabase::class.java,
            "news_db"
        ).build()


    @Provides
    @Singleton
    fun providesRepositoryImpl(
        apiService: ApiService
    ): NewsRepository = NewsRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun providesDbRepoImpl(
        @ApplicationContext app: Context
    ): FavoriteNewsRepository = FavoriteNewsRepositoryImpl(app)
}