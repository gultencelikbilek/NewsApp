package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.presentation.news_detail.NewsDetailsScreen
import com.example.newsapp.presentation.news_list.NewsListScreen

@Composable
fun AppNavigation(
) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =Screen.NewsListScreen.route ){
        composable(Screen.NewsListScreen.route){
            NewsListScreen(navHostController = navController )
        }
        composable(
         route =Screen.NewsDetailScreen.route + "?url={url}",
            arguments = listOf(
                navArgument("url"){type =  NavType.StringType}
            )
        ){navBackEntry ->
            NewsDetailsScreen(
                navController,
                url  = navBackEntry.arguments?.getString("url")
            )

        }
    }

}