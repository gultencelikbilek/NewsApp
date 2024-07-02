package com.example.newsapp.navigation

sealed class Screen(val route : String) {
    object NewsListScreen : Screen("news_list_screen")
    object NewsDetailScreen : Screen("news_detail_screen")
    object FavoriteNewsScreen : Screen("favorite_news_screen")
}