package com.example.newsapp.domain.repository

import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.entity.News
import io.reactivex.rxjava3.core.Single

interface NewsRepository {
    fun getNewsListByCategory(category: Category): Single<List<News>>
}