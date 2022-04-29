package com.example.newsapp.ui.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.ChildItemLayoutBinding
import com.example.newsapp.domain.entity.News

class ChildViewHolder(private val binding: ChildItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(news: News){
        Log.d("Debug", "ChildVH bind")
        binding.apply {
            titleTextView.text = news.title
            posterImageView.load(news.urlToImage){
                placeholder(R.drawable.placeholder_item_news)
            }
        }
    }
}