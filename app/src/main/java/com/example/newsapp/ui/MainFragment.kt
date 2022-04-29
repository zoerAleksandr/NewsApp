package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.newsapp.R
import com.example.newsapp.app
import com.example.newsapp.databinding.FragmentMainBinding
import com.example.newsapp.ui.adapters.ParentRecyclerViewAdapter

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding()
    private val adapter: ParentRecyclerViewAdapter by lazy { ParentRecyclerViewAdapter() }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(app.getNewsByCategoryUseCase)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getState().observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }
        binding.parentRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), VERTICAL, false)
        binding.parentRecyclerView.adapter = adapter
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                Log.d("Debug", "Success")
                adapter.setData(appState.data)
            }
            is AppState.Loading -> {
                Log.d("Debug", "Loading")
            }
            is AppState.Error -> {
                Log.d("Debug", "Error ${appState.throwable}")
            }
        }
    }
}