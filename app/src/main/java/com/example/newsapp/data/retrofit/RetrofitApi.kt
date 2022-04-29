package com.example.newsapp.data.retrofit

import com.example.newsapp.data.retrofit.entity.ResponseByCategoryDTO
import com.example.newsapp.domain.entity.Category
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("top-headlines")
    fun getNewsByCategory(
        @Query("category") category: Category,
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Single<ResponseByCategoryDTO>
}