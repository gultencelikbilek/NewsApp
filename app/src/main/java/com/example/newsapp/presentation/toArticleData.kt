package com.example.newsapp.presentation

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.ArticleData

fun Article.toArticleData(id: Int = 0): ArticleData {
    return ArticleData(
        id = id, // ID'yi veritabanında otomatik artan bir değer olarak bırakabilirsiniz
        author = this.author,
        content = this.content,
        title = this.title,
        url = this.url
    )
}