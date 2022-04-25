package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.entity.News
import com.example.newsapp.domain.repository.NewsRepository
import io.reactivex.rxjava3.core.Single

class GetNewsByCategoryUseCase(private val repository: NewsRepository) {
    fun execute(category: Category): Single<List<News>> {
        return repository.getNewsListByCategory(category)
    }
}