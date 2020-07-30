package com.example.moviereviewer.repository.pojo

import android.util.Log
import com.example.moviereviewer.datasources.api.ApiFactory
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


object Repository {

    const val API_KEY = "978314745d3ce652ac32e226b079bf48"
    var compositeDisposable = CompositeDisposable()

    fun getResult() {
        val disposable = ApiFactory.create().getMovies(API_KEY, "RU", "popularity.desc", 1000, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val movies: List<Movie>? = it?.results
                if (movies != null) {
                    for (m in movies) {
                        Log.i("checkMovie", m.title)
                    }
                } else {
                    Log.i("error", "error")
                }
            }
        compositeDisposable.add(disposable)
    }
}