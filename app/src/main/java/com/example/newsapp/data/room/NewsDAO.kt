package com.example.newsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.domain.entity.Category

@Dao
interface NewsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(list: List<NewsRoomDTO>)

    @Query("SELECT * FROM NewsRoomDTO WHERE category =:category")
    fun getNewsList(category: Category): List<NewsRoomDTO>
}