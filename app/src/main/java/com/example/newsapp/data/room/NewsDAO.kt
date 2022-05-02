package com.example.newsapp.data.room

import androidx.room.*
import com.example.newsapp.domain.entity.Category
import io.reactivex.rxjava3.core.Single

@Dao
interface NewsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(list: List<NewsRoomDTO>)

    @Query("SELECT * FROM NewsRoomDTO WHERE category =:category")
    fun getNewsList(category: Category): Single<List<NewsRoomDTO>>

    @Query("DELETE from NewsRoomDTO WHERE category =:category")
    fun deleteByCategory(category: String)

    @Transaction
    fun updateNewsByCategory(category: String, newsList: List<NewsRoomDTO>){
        deleteByCategory(category)
        insertNews(newsList)
    }
}