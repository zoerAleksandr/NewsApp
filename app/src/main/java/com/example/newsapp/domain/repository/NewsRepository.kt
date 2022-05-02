package com.example.newsapp.domain.repository

import com.example.newsapp.data.room.NewsRoomDTO
import io.reactivex.rxjava3.core.Single

interface NewsRepository {
    fun getNewsListByCategory(category: String): Single<List<NewsRoomDTO>>
}