package com.example.newsapp.presentation.news_detail.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

object DataStoreManager {
    private val Context.favoriteNewsDataStore: DataStore<FavoriteNewsSettings> by dataStore(
        // `favoriteNewsDataStore` isimli bir DataStore tanımlanır ve bu DataStore
        // `FavoriteNewsSettings` tipinde veri saklar. `FavoriteNewsSettingsSerializer` kullanılarak JSON formatında serileştirilir.
        fileName = "favorite_news_settings.json",
        serializer = FavoriteNewsSettingsSerializer
    )

    // `addFavoriteNews` fonksiyonu, verilen haber ID'sini favori haberler listesine ekler.
    // `updateData` ile mevcut verileri güncelleriz.
    suspend fun addFavoriteNews(context: Context, newsId: String) {
        context.favoriteNewsDataStore.updateData { settings ->
            // `settings` nesnesinin `favoriteNewsIds` set'ine yeni haber ID'sini ekleriz.
            settings.copy(favoriteNewsIds = settings.favoriteNewsIds + newsId)
        }
    }

    // `removeFavoriteNews` fonksiyonu, verilen haber ID'sini favori haberler listesinden çıkarır.
    // `updateData` ile mevcut verileri güncelleriz.
    suspend fun removeFavoriteNews(context: Context, newsId: String) {
        context.favoriteNewsDataStore.updateData { settings ->
            // `settings` nesnesinin `favoriteNewsIds` set'inden belirli haber ID'sini çıkarırız.
            settings.copy(favoriteNewsIds = settings.favoriteNewsIds - newsId)
        }
    }
    // `getFavoriteNews` fonksiyonu, favori haberler listesini döndürür.
    // `Flow` kullanarak veri akışını yönetiriz ve olası hataları yakalarız.
    fun getFavoriteNews(context: Context): Flow<Set<String>> {
        return context.favoriteNewsDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(FavoriteNewsSettings())
                } else {
                    throw exception
                }
            }
            // `map` kullanarak `FavoriteNewsSettings` içindeki `favoriteNewsIds` set'ini döndürür.
            .map { it.favoriteNewsIds }
    }
}