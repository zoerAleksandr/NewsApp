package com.example.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.usecase.GetNewsByKeyWord
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchResultViewModel : ViewModel(), KoinComponent {
    private val useCase: GetNewsByKeyWord by inject()
    private val liveData: MutableLiveData<AppState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun getData(request: String): LiveData<AppState> {
        loadingData(request)
        return liveData
    }

    private fun loadingData(request: String) {
        liveData.postValue(AppState.Loading(true))
        compositeDisposable.add(
            Single.just(request)
                .observeOn(Schedulers.io())
                .flatMap { useCase.execute(request) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        liveData.postValue(
                            AppState.Success(Category.GENERAL to it)
                        )
                    },
                    onError = {
                        liveData.postValue(
                            AppState.Error(it)
                        )
                    }
                )
        )
        useCase.execute(request)
    }
}