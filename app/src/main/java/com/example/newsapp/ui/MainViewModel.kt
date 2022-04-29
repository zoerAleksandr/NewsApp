package com.example.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.usecase.GetNewsByCategoryUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase
) : ViewModel() {
    private val liveData: MutableLiveData<AppState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun getState(): LiveData<AppState> {
        executeRequest()
        return liveData
    }

    private fun executeRequest() {
        liveData.postValue(AppState.Loading(true))
        val categories = Category.values().sortedBy { it.name }
        var currentCategory = -1
        compositeDisposable.add(
            Observable.fromIterable(categories)
                .subscribeOn(Schedulers.io())
                .concatMap { category ->
                    getNewsByCategoryUseCase.execute(category).toObservable()
                }
                .doOnNext { currentCategory += 1 }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        liveData.postValue(AppState.Success(Pair(categories[currentCategory], it)))
                    },
                    onError = { error ->
                        liveData.postValue(AppState.Error(error))
                    }
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}