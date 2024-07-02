package com.example.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.AppBottomNavigation
import com.example.newsapp.navigation.AppNavigation
import com.example.newsapp.presentation.news_list.NewsListScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        AppBottomNavigation(navController = navController)
                    }
                ) {
                    Column {
                        AppNavigation(navController)
                    }
                }
            }
        }
    }
}
