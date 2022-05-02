package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.entity.News
import com.example.newsapp.domain.repository.NewsRepository
import io.reactivex.rxjava3.core.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetNewsByKeyWord() : KoinComponent {
    private val repository: NewsRepository by inject()
    fun execute(keyWord: String): Single<List<News>> {
        return repository.getNewsByKeyWord(keyWord)
    }
}