package com.example.newsapp.presentation.news_detail.datastore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreNewsViewModel @Inject constructor(application: Application) : AndroidViewModel(application){
    // `MutableStateFlow` ve `StateFlow` kullanarak favori haberlerin listesi saklanır.
    // `MutableStateFlow` sadece ViewModel içinde değiştirilebilirken, `StateFlow` dışarıdan sadece okunabilir.
    private val _favoriteNews = MutableStateFlow<Set<String>>(emptySet())
    val favoriteNews: StateFlow<Set<String>> = _favoriteNews

    // ViewModel oluşturulduğunda, favori haberlerin akışını başlatır ve günceller.
    init {
        viewModelScope.launch {
            DataStoreManager.getFavoriteNews(application).collect { favorites ->
                _favoriteNews.value = favorites
            }
        }
    }
    // Yeni bir haber ID'sini favorilere ekler.
    fun addFavoriteNews(newsId: String) {
        viewModelScope.launch {
            DataStoreManager.addFavoriteNews(getApplication(), newsId)
        }
    }
    // Bir haber ID'sini favorilerden çıkarır.
    fun removeFavoriteNews(newsId: String) {
        viewModelScope.launch {
            DataStoreManager.removeFavoriteNews(getApplication(), newsId)
        }
    }

    fun isFavorite(newsId: String): Boolean {
        return favoriteNews.value.contains(newsId)
    }
}