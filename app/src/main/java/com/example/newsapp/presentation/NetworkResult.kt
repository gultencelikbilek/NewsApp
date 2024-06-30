package com.example.newsapp.presentation

sealed class NetworkResult<out T> {

    data class SuccessNews<out T>(val data : T) : NetworkResult<T>()
    data class Failure(val msg : String) : NetworkResult<Nothing>()
    object Loading :NetworkResult<Nothing>()
}