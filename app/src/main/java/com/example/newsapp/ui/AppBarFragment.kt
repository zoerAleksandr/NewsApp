package com.example.newsapp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentAppBarBinding


/**
 * Не забудь отнаследовать активити от контроллера
 */
const val KEY_NEWS = "key_news"

class AppBarFragment : Fragment(R.layout.fragment_app_bar) {
    private val binding: FragmentAppBarBinding by viewBinding()
    private val controller by lazy { activity as Controller }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is Controller) {
            throw IllegalStateException("Activity должна наследоваться от MainFragment.Controller")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchLayout.setStartIconOnClickListener {
            val bundle = Bundle()
            val request = binding.searchEditText.text.toString()
            bundle.putString(KEY_NEWS, request)
            controller.openSearchFragment(bundle)
        }
    }
}