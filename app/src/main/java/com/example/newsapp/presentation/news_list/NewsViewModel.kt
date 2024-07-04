package com.example.newsapp.presentation.news_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.usecase.AddNewsUseCase
import com.example.newsapp.data.usecase.GetNewsListUseCase
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.ArticleData
import com.example.newsapp.presentation.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val useCase: GetNewsListUseCase,
    private val addNewsUseCase: AddNewsUseCase
) : ViewModel() {

    private val _newsState = mutableStateOf(NewsState())
    val newsState: State<NewsState> = _newsState


    private val _favNewsState = mutableStateOf(FavoriteNewsState())
    val favNewsState: State<FavoriteNewsState> = _favNewsState

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
                Log.d("succes", _favNewsState.value.toString())
            } catch (e: Exception) {
                _favNewsState.value = FavoriteNewsState(isError = e.message)
            }
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