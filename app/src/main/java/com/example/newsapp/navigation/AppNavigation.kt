package com.example.newsapp.navigation

import android.content.Context
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.newsapp.R
import com.example.newsapp.presentation.news_detail.NewsDetailsScreen
import com.example.newsapp.presentation.news_detail.datastore.FavoriteNewsSettings
import com.example.newsapp.presentation.news_detail.datastore.FavoriteNewsSettingsSerializer
import com.example.newsapp.presentation.news_list.NewsListScreen
import com.example.newsapp.presentation.saved_screen.FavoriteNewsScreen


sealed class BottomNavItem(
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val label: String
) {

    object Discover : BottomNavItem(
        Screen.NewsListScreen.route,
        R.drawable.discover_full,
        R.drawable.discover,
        "Discover"
    )

    object Saved : BottomNavItem(
        Screen.FavoriteNewsScreen.route,
        R.drawable.save_full,
        R.drawable.save,
        "Saved"
    )
}
private val Context.favoriteNewsDataStore: DataStore<FavoriteNewsSettings> by dataStore(
    fileName = "favorite_news_settings.json",
    serializer = FavoriteNewsSettingsSerializer
)
@Composable
fun AppBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Discover,
        BottomNavItem.Saved
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                val color = if (isSelected) Color.Black else Color.Gray
                val targetColor by animateColorAsState(targetValue = color)
                val targetFontWeight =
                    if (isSelected) FontWeight.ExtraBold else FontWeight.Bold
                val icon = if (isSelected) item.selectedIcon else item.unselectedIcon

                BottomNavigationItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = item.label,
                            tint = targetColor,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 9.sp,
                            color = targetColor,
                            fontWeight = targetFontWeight
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(
    navController: NavHostController
) {
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Screen.NewsListScreen.route) {
            composable(Screen.NewsListScreen.route) {
                NewsListScreen(
                    animatedVisibilityScope = this,
                    navController
                )
            }
            composable(
                route = Screen.NewsDetailScreen.route + "?url={url}&urlToImage={urlToImage}&title={title}&author={author}&content={content}&name={name}",
                arguments = listOf(
                    navArgument("url") { type = NavType.StringType },
                    navArgument("urlToImage") { type = NavType.StringType },
                    navArgument("title") { type = NavType.StringType },
                    navArgument("author") { type = NavType.StringType },
                    navArgument("content") { type = NavType.StringType },
                    navArgument("name") { type = NavType.StringType }
                )
            ) { navBackEntry ->
                NewsDetailsScreen(
                    animatedVisibilityScope = this,
                    navController,
                    url = navBackEntry.arguments?.getString("url"),
                    urlToImage = navBackEntry.arguments?.getString("urlToImage"),
                    title = navBackEntry.arguments?.getString("title"),
                    author = navBackEntry.arguments?.getString("author"),
                    content = navBackEntry.arguments?.getString("content"),
                    name = navBackEntry.arguments?.getString("name")
                )
            }
            composable(Screen.FavoriteNewsScreen.route){
                FavoriteNewsScreen(navController)
            }
        }
    }
}
