package com.example.newsapp.presentation.news_list

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.presentation.NetworkResult
import com.example.newsapp.presentation.NewsListCardComponent

@Composable
fun NewsListScreen(
    navHostController: NavHostController,
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
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Column {
                                Text(
                                    text = stringResource(id = R.string.discover),
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = stringResource(id = R.string.news_from_around),
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                        color = Color.Gray
                                    )
                                )
                            }
                        },
                        backgroundColor = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp),
                content = { paddingValues ->
                    LazyColumn(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        items(state.data.data) {
                            NewsListCardComponent(it,navHostController,it.url)
                        }
                    }
                }
            )
        }

        null -> {
            Log.d("errorscreen:", state.isError.toString())
        }
    }
}

