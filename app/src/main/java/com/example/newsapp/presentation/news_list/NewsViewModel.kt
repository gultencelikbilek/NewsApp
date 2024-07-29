package com.example.newsapp.presentation.news_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.usecase.AddNewsUseCase
import com.example.newsapp.data.usecase.DeleteNewsUseCase
import com.example.newsapp.data.usecase.GetNewsListUseCase
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.ArticleData
import com.example.newsapp.presentation.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val useCase: GetNewsListUseCase,
    private val addNewsUseCase: AddNewsUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase
) : ViewModel() {

    private val _newsState = mutableStateOf(NewsState())
    val newsState: State<NewsState> = _newsState

    private val _favNewsState = mutableStateOf(FavoriteNewsState())
    val favNewsState: State<FavoriteNewsState> = _favNewsState

    private val _deleteNewsState = mutableStateOf(FavoriteNewsState())
    val deleteNewsState : State<FavoriteNewsState> = _deleteNewsState

    private val _isAdded = MutableStateFlow<Boolean?>(null)
    val isAdded: StateFlow<Boolean?> = _isAdded
    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            useCase.invoke().collect { result ->
                when (result) {
                    is NetworkResult.Failure -> {
                        _newsState.value = NewsState(
                            isError = result.msg
                        )
                        Log.d("newslistviewmodel:", result.msg)
                    }

                    is NetworkResult.Loading -> {
                        _newsState.value = NewsState(
                            isLoading = true
                        )
                    }

                    is NetworkResult.SuccessNews -> {
                        _newsState.value = NewsState(
                            data = result,
                            isError = null,
                            isLoading = false
                        )

                        Log.d("newslistviewmodel:", result.data.toString())
                    }
                }
            }
        }
    }

    fun addNews(articledata: ArticleData) {
        viewModelScope.launch {
            try {
                addNewsUseCase.invoke(articledata)
                _favNewsState.value = FavoriteNewsState(success = true)
                _isAdded.value = true
                Log.d("succes", _favNewsState.value.toString())
            } catch (e: Exception) {
                _favNewsState.value = FavoriteNewsState(isError = e.message)
                _isAdded.value = false
            }
        }
    }

    fun deleteNews(articledata: ArticleData) = viewModelScope.launch {
        try {
            deleteNewsUseCase.invoke(articledata)
            _deleteNewsState.value = FavoriteNewsState(success = true)
        }catch (e : Exception){
            _favNewsState.value = FavoriteNewsState(isError = e.message)
            _isAdded.value = false
        }
    }
}

data class NewsState(
    val data: NetworkResult<List<Article>>? = null,
    val isError: String? = "",
    val isLoading: Boolean? = false
)

data class FavoriteNewsState(
    val success: Boolean = false,
    val isError: String? = null
)