package com.example.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.domain.usecase.GetNewsByCategoryUseCase

class MainViewModelFactory(private val useCase: GetNewsByCategoryUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetNewsByCategoryUseCase::class.java).newInstance(useCase)
    }
}