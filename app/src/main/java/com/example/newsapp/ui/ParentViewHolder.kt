package com.example.newsapp.ui

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.example.newsapp.databinding.ParentItemLayoutBinding
import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.entity.News

class ParentViewHolder(
    private val binding: ParentItemLayoutBinding,
    private val newsList: List<News>
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(category: Category) {
        Log.d("Debug", "ParentVH bind")
        val childAdapter = ChildRecyclerViewAdapter(newsList)
        binding.apply {
            childRecyclerView.adapter = childAdapter
            childRecyclerView.layoutManager =
                LinearLayoutManager(binding.root.context, HORIZONTAL, false)
            labelTextView.text = category.name
        }
    }
}