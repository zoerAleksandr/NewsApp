package com.example.newsapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsRoomDTO::class], version = 1, exportSchema = false)
abstract class NewsDataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDAO
}