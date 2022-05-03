package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Controller {
    private val binding: ActivityMainBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.containerForAppBar.id, AppBarFragment())
                .commitNow()

            supportFragmentManager.beginTransaction()
                .add(binding.containerForNewsLayout.id, NewsByCategoriesFragment())
                .commitNow()
        }
    }

    override fun openSearchFragment(request: Bundle) {
        supportFragmentManager.beginTransaction()
            .replace(binding.containerForNewsLayout.id, SearchResultFragment.newInstance(request))
            .addToBackStack(null)
            .commit()
    }
}