package com.example.newsapp.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class ResponseByCategoryDTO(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: String,
    @SerializedName("articles") val articles: List<NewsRetrofitDTO>
)