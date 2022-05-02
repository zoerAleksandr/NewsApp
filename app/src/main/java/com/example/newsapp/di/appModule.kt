package com.example.newsapp.di

import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.WorkManager
import com.example.newsapp.data.retrofit.RetrofitClient
import com.example.newsapp.data.retrofit.repository.RetrofitNewsRepositoryImpl
import com.example.newsapp.data.room.NewsDataBase
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.usecase.GetNewsByCategoryUseCase
import com.example.newsapp.domain.usecase.GetNewsByKeyWord
import com.example.newsapp.ui.MainViewModel
import com.example.newsapp.ui.SearchResultViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitClient().retrofitApi }
    single<NewsRepository> { RetrofitNewsRepositoryImpl() }
    single { GetNewsByKeyWord() }
    single { GetNewsByCategoryUseCase(get()) }
    single {
        Room.databaseBuilder(androidContext(), NewsDataBase::class.java, "News.db")
            .build()
    }
    single { get<NewsDataBase>().newsDao() }
    single { WorkManager.getInstance(androidContext()) }
    single {
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    viewModel {
        MainViewModel(get())
    }
    viewModel{
        SearchResultViewModel()
    }
}