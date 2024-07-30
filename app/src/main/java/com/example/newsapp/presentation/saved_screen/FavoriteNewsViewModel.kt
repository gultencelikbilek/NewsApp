package com.example.newsapp.presentation.saved_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.usecase.DeleteNewsUseCase
import com.example.newsapp.data.usecase.GetFavNewsListUseCase
import com.example.newsapp.domain.model.ArticleData
import com.example.newsapp.presentation.news_list.FavoriteNewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsViewModel @Inject constructor(
    private val getFavNewsListUseCase: GetFavNewsListUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase
) :
    ViewModel() {

    private val _favNewsState = mutableStateOf(FavNewsState())
    val favNewsState: State<FavNewsState> = _favNewsState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    private val _deleteNewsState = mutableStateOf(FavoriteNewsState())
    val deleteNewsState : State<FavoriteNewsState> = _deleteNewsState

    fun getFavNews() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _favNewsState.value = FavNewsState(
                    data = getFavNewsListUseCase.invoke()
                )
            } catch (e: Exception) {
                _favNewsState.value = FavNewsState(isError = e.message ?: "Unknown error")
            }
            _isLoading.value = false
        }
    }

    fun deleteNews(articleData: ArticleData) {
        viewModelScope.launch {
            try {
                deleteNewsUseCase.invoke(articleData)
                getFavNews() // Verileri güncelle
            } catch (e: Exception) {
                _favNewsState.value = FavNewsState(isError = e.message ?: "Unknown error")
            }
        }
    }
}


data class FavNewsState(
    val data: List<ArticleData>? = null,
    val isError: String? = ""
)


