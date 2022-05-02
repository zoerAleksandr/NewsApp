package com.example.newsapp.data.retrofit.repository

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.retrofit.RetrofitApi
import com.example.newsapp.data.room.NewsRoomDTO
import com.example.newsapp.domain.entity.News
import com.example.newsapp.domain.repository.NewsRepository
import io.reactivex.rxjava3.core.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RetrofitNewsRepositoryImpl : NewsRepository, KoinComponent {
    private val api: RetrofitApi by inject()
    override fun getNewsListByCategory(category: String): Single<List<NewsRoomDTO>> {
        return api
            .getNewsByCategory(category, "ru", BuildConfig.NEWS_API_KEY)
            .map { response ->
                response.articles
                    .map { newsDto ->
                        NewsRoomDTO(
                            id = 0,
                            category = category,
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

    override fun getNewsByKeyWord(keyWord: String): Single<List<News>> {
        return api.getNewsByKeyWord(keyWord, "ru", BuildConfig.NEWS_API_KEY)
            .map { response ->
                response.articles
                    .map { newsRetrofitDTO ->
                        News(
                            newsRetrofitDTO.author,
                            newsRetrofitDTO.title,
                            newsRetrofitDTO.description,
                            newsRetrofitDTO.url,
                            newsRetrofitDTO.urlToImage,
                            newsRetrofitDTO.publishedAt,
                            newsRetrofitDTO.content
                        )
                    }
            }
    }
}