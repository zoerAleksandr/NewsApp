package com.example.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ParentItemLayoutBinding
import com.example.newsapp.domain.entity.Category


class ParentRecyclerViewAdapter :
    RecyclerView.Adapter<ParentViewHolder>() {

    private val categories = Category.values().sortedBy { it.name }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ParentItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ParentViewHolder(binding, listOf())
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size
}