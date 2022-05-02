package com.example.newsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSearchResultBinding
import com.example.newsapp.domain.entity.News
import com.example.newsapp.ui.adapters.SearchRecyclerViewAdapter
import org.koin.android.ext.android.inject

class SearchResultFragment : Fragment(R.layout.fragment_search_result) {
    companion object {
        fun newInstance(bundle: Bundle?): SearchResultFragment {
            val fragment = SearchResultFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val binding: FragmentSearchResultBinding by viewBinding()
    private val adapter: SearchRecyclerViewAdapter by lazy {
        SearchRecyclerViewAdapter { news ->
            showNewsInBrowser(news)
        }
    }
    private val viewModel: SearchResultViewModel by inject()
    private fun showNewsInBrowser(news: News) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
                data = Uri.parse(news.url)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.error_browsable),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
        arguments?.getString(KEY_NEWS)?.let {
            viewModel.getData(it).observe(viewLifecycleOwner) { state ->
                renderData(state)
            }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressLayout.visibility = View.GONE
                adapter.setData(appState.data.second)
            }
            is AppState.SuccessOldData -> {

            }
            is AppState.Loading -> {
                binding.recyclerView.visibility = View.GONE
                binding.progressLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {

            }
        }
    }
}