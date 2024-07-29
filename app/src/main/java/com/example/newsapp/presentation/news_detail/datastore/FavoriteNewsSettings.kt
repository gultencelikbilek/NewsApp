package com.example.newsapp.presentation.news_detail.datastore

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteNewsSettings(
    //favoriteNewsIds: Set<String> alan, favori haberlerin ID'lerini saklar.
    //Set kullanılması, aynı ID'nin birden fazla kez eklenmesini engeller ve benzersiz ID'ler kümesini korur.
    val favoriteNewsIds: Set<String> = emptySet() //emptySet() ise boş bir Set oluşturur. Yani başlangıçta hiçbir favori haber yoktur.
)