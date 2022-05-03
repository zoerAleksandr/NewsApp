package com.example.newsapp.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.SearchResultItemLayoutBinding
import com.example.newsapp.domain.entity.News

class SearchViewHolder(private val binding: SearchResultItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(news: News, listener: (News) -> Unit) {
        binding.apply {
            titleTextView.text = news.title
            posterImageView.load(news.urlToImage) {
                placeholder(R.drawable.placeholder_item_news)
            }
            root.setOnClickListener {
                listener.invoke(news)
            }
        }
    }
}