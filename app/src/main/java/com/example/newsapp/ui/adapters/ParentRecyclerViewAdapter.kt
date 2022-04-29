package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ParentItemLayoutBinding
import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.entity.News


class ParentRecyclerViewAdapter :
    RecyclerView.Adapter<ParentViewHolder>() {
    private var listCategory: MutableList<Pair<Category, List<News>>> = mutableListOf()

    fun setData(newData: Pair<Category, List<News>>) {
        listCategory.add(newData)
        val nextIndex = listCategory.size - 1
        notifyItemInserted(nextIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ParentItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(listCategory[position].first, listCategory[position].second)
    }

    override fun getItemCount() = listCategory.size
}