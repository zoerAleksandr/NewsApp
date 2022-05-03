package com.example.newsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsByCategoriesBinding
import com.example.newsapp.domain.entity.News
import com.example.newsapp.ui.adapters.ParentRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsByCategoriesFragment : Fragment(R.layout.fragment_news_by_categories) {
    private val binding: FragmentNewsByCategoriesBinding by viewBinding()

    private val adapter: ParentRecyclerViewAdapter by lazy {
        ParentRecyclerViewAdapter { news ->
            showNewsInBrowser(news)
        }
    }
    private val mainViewModel: MainViewModel by viewModel()
    private var snackBar: Snackbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getState().observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }
        binding.parentRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), VERTICAL, false)
        binding.parentRecyclerView.adapter = adapter
    }

    private fun snackBarShow() {
        snackBar = Snackbar.make(
            binding.root,
            resources.getString(R.string.warning_loading_old_data),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(resources.getString(R.string.action_button_warning_loading_old_data)) {
                mainViewModel.getState()
            }
        snackBar?.show()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                adapter.setData(appState.data)
                binding.loadingLayout.visibility = View.GONE
                binding.parentRecyclerView.visibility = View.VISIBLE
            }
            is AppState.SuccessOldData -> {
                adapter.setData(appState.data)
                binding.errorLayout.visibility = View.GONE
                binding.loadingLayout.visibility = View.GONE
                binding.parentRecyclerView.visibility = View.VISIBLE
                snackBarShow()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.parentRecyclerView.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.errorLayout.visibility = View.VISIBLE
                binding.errorTextView.text = appState.throwable.message
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        snackBar?.takeIf { it.isShown }?.dismiss()
    }

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
}