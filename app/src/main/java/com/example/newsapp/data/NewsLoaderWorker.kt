package com.example.newsapp.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.WorkerParameters
import androidx.work.rxjava3.RxWorker
import com.example.newsapp.data.room.NewsDAO
import com.example.newsapp.data.room.NewsRoomDTO
import com.example.newsapp.domain.entity.Category
import com.example.newsapp.domain.usecase.GetNewsByCategoryUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewsLoaderWorker(
    context: Context,
    params: WorkerParameters
) :
    RxWorker(context, params), KoinComponent {
    private val newsDAO: NewsDAO by inject()
    private val useCase: GetNewsByCategoryUseCase by inject()
    private val categories = Category.values().sortedBy { it.name }

    private fun saveListToRoom(category: String, list: List<NewsRoomDTO>) {
        newsDAO.updateNewsByCategory(category, list)
    }

    @SuppressLint("RestrictedApi")
    override fun createWork(): Single<Result> {
        var result = Result.success()
        var currentCategory = -1
        try {
            Observable.fromIterable(categories)
                .concatMap { category ->
                    useCase.execute(category.name).toObservable()
                }
                .doOnNext { currentCategory += 1 }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        Log.d("Debug onNext", "")
                        saveListToRoom(categories[currentCategory].name, it)
                        result = Result.Retry()
                    },
                    onComplete = {
                        Log.d("Debug onComplete", "")
                        result = Result.Success()
                    },
                    onError = {
                        Log.d("Debug onError", "")
                        result = Result.Failure()
                    }
                )
            return Single.just(result)
        } catch (e: Exception) {
            return Single.error(e)
        }
    }
}