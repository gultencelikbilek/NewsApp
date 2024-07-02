package com.example.newsapp.presentation.news_detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.R
import com.example.newsapp.presentation.HeaderDetailText
import com.example.newsapp.presentation.ImageNewsDetailComponent
import com.example.newsapp.presentation.NewsCompanyNameComponent
import com.example.newsapp.presentation.NewsContentComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.NewsDetailsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    navController: NavHostController,
    url: String?,
    urlToImage: String?,
    title: String?,
    author: String?,
    content: String?,
    name: String?,
) {

    val sheetState = rememberModalBottomSheetState()
    val isSheetOpen = rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val uri = Uri.parse(url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.worldwide),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(onClick = {
                        isSheetOpen.value = true
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.share),
                            contentDescription = "Share",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                val painter = rememberAsyncImagePainter(model = urlToImage)
                if (title != null) {
                    HeaderDetailText(title)
                }

                Spacer(modifier = Modifier.height(10.dp))
                ImageNewsDetailComponent(painter)
                Spacer(modifier = Modifier.height(8.dp))
                if (name != null) {
                    NewsCompanyNameComponent(name)
                }
                if (content != null) {
                    NewsContentComponent(content)
                }
            }
        }
    )

    if (isSheetOpen.value) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen.value = false
                scope.launch {
                    sheetState.hide()
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Share",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            val contentToShare = url
                            shareViaWhatsApp(context, contentToShare.toString())
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_whatsapp),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "WhatsApp")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            val contentToShare = url
                            shareViaEmail(context,"",contentToShare.toString())
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gmail),
                        contentDescription = "E-posta ile paylaş",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Email")
                }
            }
        }
    }
}

fun shareViaWhatsApp(context: Context, contentToShare: String) {
    val whatsappIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        setPackage("com.whatsapp")
        putExtra(Intent.EXTRA_TEXT, contentToShare)
    }
    try {
        context.startActivity(whatsappIntent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun shareViaEmail(context: Context, subject: String, contentToShare: String) {
    val emailIntent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, contentToShare)
    }
    try {
        context.startActivity(Intent.createChooser(emailIntent, "E-posta ile paylaş"))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "E-posta uygulaması bulunamadı", Toast.LENGTH_SHORT).show()
    }
}