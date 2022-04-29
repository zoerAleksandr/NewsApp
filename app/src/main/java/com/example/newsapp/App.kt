package com.example.newsapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.newsapp.data.retrofit.RetrofitClient
import com.example.newsapp.data.retrofit.repository.RetrofitNewsRepositoryImpl
import com.example.newsapp.data.room.NewsDAO
import com.example.newsapp.data.room.NewsDataBase
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.usecase.GetNewsByCategoryUseCase

class App : Application() {
    companion object {
        private const val DB_NAME = "News.db"
        private var appInstance: App? = null
        private var db: NewsDataBase? = null
        fun getNewsDao(): NewsDAO {
            if (db == null) {
                synchronized(NewsDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            NewsDataBase::class.java,
                            DB_NAME
                        ).allowMainThreadQueries().build()
                    }
                }
            }
            return db!!.newsDao()
        }
    }

    private val retrofitClient = RetrofitClient()
    private val newsRepository: NewsRepository = RetrofitNewsRepositoryImpl(retrofitClient)
    val getNewsByCategoryUseCase = GetNewsByCategoryUseCase(newsRepository)
}

val Context.app: App
    get() = applicationContext as App

val Fragment.app: App
    get() = requireActivity().app