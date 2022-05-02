package com.example.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.room.NewsDAO
import com.example.newsapp.data.room.NewsRoomDTO
import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.entity.News
import com.example.newsapp.domain.usecase.GetNewsByCategoryUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
) : ViewModel(), KoinComponent {
    private val liveData: MutableLiveData<AppState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    private val newsDAO: NewsDAO by inject()
    private val categories = Category.values().sortedBy { it.name }

    fun getState(): LiveData<AppState> {
        executeRequest()
        return liveData
    }
    private fun executeRequest() {
        liveData.postValue(AppState.Loading(true))
        var currentCategory = -1
        compositeDisposable.add(
            Observable.fromIterable(categories)
                .observeOn(Schedulers.io())
                .concatMap { category ->
                    getNewsByCategoryUseCase.execute(category.name).toObservable()
                }
                .doOnNext { currentCategory += 1 }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = { listRoomDto ->
                        saveListToRoom(categories[currentCategory].name, listRoomDto)
                        liveData.postValue(
                            AppState.Success(
                                categories[currentCategory] to convertNewsRoomDtoInNews(
                                    listRoomDto
                                )
                            )
                        )
                    },
                    onError = {
                        getDataFromLocal()
                        liveData.postValue(
                            AppState.Error(it)
                        )
                    }
                )
        )
    }

    private fun saveListToRoom(category: String, list: List<NewsRoomDTO>) {
        newsDAO.updateNewsByCategory(category, list)
    }

    private fun getDataFromLocal() {
        var currentCategory = -1
        compositeDisposable.add(
            Observable.fromIterable(categories)
                .observeOn(Schedulers.io())
                .concatMap { category ->
                    newsDAO.getNewsList(category).toObservable()
                }
                .doOnNext { currentCategory += 1 }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = { listRoomDto ->
                        val list = convertNewsRoomDtoInNews(listRoomDto)
                        liveData.postValue(
                            AppState.SuccessOldData(
                                categories[currentCategory] to list
                            )
                        )
                    },
                    onError = {
                        liveData.postValue(AppState.Error(it))
                    }
                )
        )
    }

    private fun convertNewsRoomDtoInNews(newsRoomDTO: List<NewsRoomDTO>): List<News> {
        return newsRoomDTO.map { roomDTO ->
            News(
                roomDTO.author,
                roomDTO.title,
                roomDTO.description,
                roomDTO.url,
                roomDTO.urlToImage,
                roomDTO.publishedAt,
                roomDTO.content
            )
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}