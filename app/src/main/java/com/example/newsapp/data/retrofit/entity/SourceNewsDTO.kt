package com.example.newsapp.data.retrofit.entity

import com.google.gson.annotations.SerializedName

data class SourceNewsDTO(
    @SerializedName("id")val id: String?,
    @SerializedName("name")val name: String?
)