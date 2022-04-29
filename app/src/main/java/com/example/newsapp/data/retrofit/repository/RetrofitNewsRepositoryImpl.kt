package com.example.newsapp.data.retrofit.repository

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.retrofit.RetrofitClient
import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.entity.News
import com.example.newsapp.domain.repository.NewsRepository
import io.reactivex.rxjava3.core.Single


class RetrofitNewsRepositoryImpl(private val retrofit: RetrofitClient) : NewsRepository {
    override fun getNewsListByCategory(category: Category): Single<List<News>> {
        return retrofit.retrofitApi
            .getNewsByCategory(category, "ru", BuildConfig.NEWS_API_KEY)
            .map { response ->
                response.articles
                    .map { newsDto ->
                        News(
                            newsDto.author,
                            newsDto.title,
                            newsDto.description,
                            newsDto.url,
                            newsDto.urlToImage,
                            newsDto.publishedAt,
                            newsDto.content
                        )
                    }
            }
    }
}