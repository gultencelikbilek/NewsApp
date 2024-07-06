package com.example.newsapp.presentation.saved_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    LaunchedEffect(key1 = state.data) {
        favNewsViewModel.getFavNews()
        if (state.data != null) {
            Log.d("burdadada", state.data.toString())
        } else {
            Log.d("bura", state.isError.toString())
        }
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
            if (state.data != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(state.data) {
                        FavNewsCardComponent(
                            it
                        )
                    }
                }
            }
        }
    )
}
