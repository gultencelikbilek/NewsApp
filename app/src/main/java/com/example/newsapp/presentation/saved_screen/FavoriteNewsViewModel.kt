package com.example.newsapp.presentation.saved_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.usecase.GetFavNewsListUseCase
import com.example.newsapp.domain.model.ArticleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsViewModel @Inject constructor(private val getFavNewsListUseCase: GetFavNewsListUseCase) :
    ViewModel() {

    private val _favNewsState = mutableStateOf(FavNewsState())
    val favNewsState : State<FavNewsState> = _favNewsState

    fun getFavNews() {
        viewModelScope.launch {
            _favNewsState.value = FavNewsState(
                data = getFavNewsListUseCase.invoke()
            )
        }
    }



}

data class FavNewsState(
    val data : List<ArticleData>? = null,
    val isError : String?=""
)


