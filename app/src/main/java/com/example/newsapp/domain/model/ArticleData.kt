package com.example.newsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleData(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val author: String?="",
    val content: String?="",
    val title: String?="",
    val url: String?="",
    val urlToImage: String?=""
)