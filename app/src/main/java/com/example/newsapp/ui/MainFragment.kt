package com.example.newsapp.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentMainBinding
import com.example.newsapp.domain.entity.News
import com.example.newsapp.ui.adapters.ParentRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Не забудь отнаследовать активити от контроллера
 */

const val KEY_NEWS = "key_news"

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is Controller) {
            throw IllegalStateException("Activity должна наследоваться от MainFragment.Controller")
        }
    }

    private val controller by lazy { activity as Controller }

    private val adapter: ParentRecyclerViewAdapter by lazy {
        ParentRecyclerViewAdapter { news ->
            showNewsInBrowser(news)
        }
    }
    private val mainViewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getState().observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }
        binding.parentRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), VERTICAL, false)
        binding.parentRecyclerView.adapter = adapter
        binding.searchLayout.setStartIconOnClickListener {
            val bundle = Bundle()
            val request = binding.searchEditText.text.toString()
            bundle.putString(KEY_NEWS, request)
            controller.openSearchFragment(bundle)
        }
    }

    private fun snackBarShow() {
        Snackbar.make(binding.root, "Показаны старые данные", Snackbar.LENGTH_INDEFINITE)
            .setAction("Обновить") {
                mainViewModel.getState()
            }.show()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                Log.d("Debug", "Success")
                adapter.setData(appState.data)
                binding.loadingLayout.visibility = View.GONE
                binding.parentRecyclerView.visibility = View.VISIBLE
            }
            is AppState.SuccessOldData -> {
                adapter.setData(appState.data)
                binding.loadingLayout.visibility = View.GONE
                binding.parentRecyclerView.visibility = View.VISIBLE
                snackBarShow()
            }
            is AppState.Loading -> {
                Log.d("Debug", "Loading")
                binding.loadingLayout.visibility = View.VISIBLE
                binding.parentRecyclerView.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Log.d("Debug", "Error ${appState.throwable}")
            }
        }
    }

    interface Controller {
        fun openSearchFragment(request: Bundle)
    }
}