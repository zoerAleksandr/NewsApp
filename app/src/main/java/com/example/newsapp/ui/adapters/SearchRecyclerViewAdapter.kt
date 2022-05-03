package com.example.newsapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.SearchResultItemLayoutBinding
import com.example.newsapp.domain.entity.News

class SearchRecyclerViewAdapter(
    private val listener: (News) -> Unit
) :
    RecyclerView.Adapter<SearchViewHolder>() {
    private val newsList: MutableList<News> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<News>) {
        newsList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchResultItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(newsList[position], listener)
    }

    override fun getItemCount() = newsList.size
}