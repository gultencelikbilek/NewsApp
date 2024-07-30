package com.example.newsapp.presentation.saved_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.presentation.FavNewsCardComponent

@Composable
fun FavoriteNewsScreen(
    navController: NavHostController,
    favNewsViewModel: FavoriteNewsViewModel = hiltViewModel()
) {
    val state = favNewsViewModel.favNewsState.value
    val isLoading = favNewsViewModel.isLoading.collectAsState()

    LaunchedEffect(key1 = state.data) {
        favNewsViewModel.getFavNews()

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    androidx.compose.material.Text(text = stringResource(id = R.string.fav))
                },
                backgroundColor = Color.White
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    state.data?.let { newsList ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(newsList) {
                                FavNewsCardComponent(
                                    it
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
