package com.example.newsapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsRoomDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val category: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)