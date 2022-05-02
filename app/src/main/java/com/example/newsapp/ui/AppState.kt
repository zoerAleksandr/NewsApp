package com.example.newsapp.ui

import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.entity.News

sealed class AppState {
    data class Success(val data: Pair<Category, List<News>>) : AppState()
    data class SuccessOldData(val data: Pair<Category, List<News>>) : AppState()
    data class Loading(val progress: Boolean) : AppState()
    data class Error(val throwable: Throwable) : AppState()
}