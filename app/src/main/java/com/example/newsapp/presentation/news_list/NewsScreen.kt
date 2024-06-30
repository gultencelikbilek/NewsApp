package com.example.newsapp.presentation.news_list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.presentation.NetworkResult

@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {

    val state = viewModel.newsState.value

    when (state.data) {
        is NetworkResult.Failure -> {
            Log.d("errorscreen:", state.isError.toString())
        }

        is NetworkResult.Loading -> {
            CircularProgressIndicator()
        }

        is NetworkResult.SuccessNews -> {
            Surface(
                modifier = Modifier.padding(10.dp)
            ) {
                LazyColumn {
                    items(state.data.data) {
                        Text(text = it.title)
                    }
                }
            }
        }

        null -> {
            Log.d("errorscreen:", state.isError.toString())
        }
    }
}