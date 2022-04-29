package com.example.newsapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.newsapp.data.retrofit.RetrofitClient
import com.example.newsapp.data.retrofit.repository.RetrofitNewsRepositoryImpl
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.usecase.GetNewsByCategoryUseCase

class App : Application() {
    private val retrofitClient = RetrofitClient()
    private val newsRepository: NewsRepository = RetrofitNewsRepositoryImpl(retrofitClient)
    val getNewsByCategoryUseCase = GetNewsByCategoryUseCase(newsRepository)
}

val Context.app: App
    get() = applicationContext as App

val Fragment.app: App
    get() = requireActivity().app