package com.example.newsapp.data.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://newsapi.org/v2/"

class RetrofitClient {

    private val logging = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }
    private val httpClient = OkHttpClient.Builder()
        .apply { addInterceptor(logging) }

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(httpClient.build())
        .build()

    val retrofitApi: RetrofitApi = retrofitClient.create(RetrofitApi::class.java)
}