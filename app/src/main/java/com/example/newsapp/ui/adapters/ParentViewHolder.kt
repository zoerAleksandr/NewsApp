package com.example.newsapp.ui.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.example.newsapp.databinding.ParentItemLayoutBinding
import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.entity.News

class ParentViewHolder(
    private val binding: ParentItemLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(category: Category, listNews: List<News>) {
        val childAdapter = ChildRecyclerViewAdapter(listNews)
        binding.apply {
            childRecyclerView.adapter = childAdapter
            childRecyclerView.layoutManager =
                LinearLayoutManager(binding.root.context, HORIZONTAL, false)
            labelTextView.text = category.name
        }
    }
}