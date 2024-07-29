package com.example.newsapp.presentation

import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.ArticleData
import com.example.newsapp.navigation.Screen
import com.example.newsapp.presentation.news_detail.datastore.DataStoreNewsViewModel
import com.example.newsapp.presentation.news_list.NewsViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.NewsListCardComponent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    it: Article,
    navHostController: NavHostController,
    url: String?,
    urlToImage: String?,
    title: String?,
    author: String?,
    content: String?,
    name: String?,
    newsViewModel: NewsViewModel
) {
    val viewModel: DataStoreNewsViewModel = hiltViewModel()
    val favoriteNews by viewModel.favoriteNews.collectAsState()
    val isFavorite = favoriteNews.contains(url)

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        onClick = {
            val route =
                "${
                    Screen.NewsDetailScreen.route
                }?url=${
                    Uri.encode(url)
                }?urlToImage=${
                    Uri.encode(urlToImage)
                }?title=${
                    Uri.encode(title)
                }?author=${
                    Uri.encode(author)
                }?content=${
                    Uri.encode(content)
                }?name=${Uri.encode(name)}"
            navHostController.navigate(route)
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val painter = rememberAsyncImagePainter(model = it.urlToImage)

            Image(
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 2.dp, top = 4.dp)
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.Center)
                        )
                    }

                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = it.source.name,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            color = Color.Gray
                        ),
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it.title,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.width(5.dp))

                Box(
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Row {
                        Text(
                            text = it.publishedAt,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                color = Color.Gray
                            )
                        )
                        Spacer(modifier = Modifier.width(80.dp))

                        IconButton(onClick = {
                            if (isFavorite) {
                                viewModel.removeFavoriteNews(url!!)
                                val articleData = ArticleData(
                                    0, author, content, title, url
                                )
                                newsViewModel.deleteNews(url)
                            } else {
                                viewModel.addFavoriteNews(url!!)
                                val articleData = ArticleData(
                                    0, author, content, title, url
                                )
                                newsViewModel.addNews(articleData)
                            }
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = if (isFavorite) R.drawable.save_full else R.drawable.save
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun HeaderDetailText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        ),
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun ImageNewsDetailComponent(painter: Painter) {
    Image(
        painter = painter,
        contentDescription = "",
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun NewsCompanyNameComponent(name: String) {
    Row(
        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = name,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = Color.Gray
            ),
            modifier = Modifier.padding(top = 3.dp)
        )
    }
}

@Composable
fun NewsContentComponent(content: String) {

    Text(
        modifier = Modifier.padding(16.dp),
        text = content,
        style = TextStyle(
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.Gray
        )
    )
}

@Composable
fun FavNewsCardComponent(articleData: ArticleData) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp),

        ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val painter = rememberAsyncImagePainter(model = articleData.urlToImage)
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = articleData.title.toString(),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = articleData.author.toString(),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}