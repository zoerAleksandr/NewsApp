package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ChildItemLayoutBinding
import com.example.newsapp.domain.entity.News

class ChildRecyclerViewAdapter(
    list: List<News>,
    private val listener: (News) -> Unit
) :
    RecyclerView.Adapter<ChildViewHolder>() {
    private val newsList: MutableList<News> = mutableListOf()

    init {
        newsList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ChildItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(newsList[position], listener)
    }

    override fun getItemCount() = newsList.size
}