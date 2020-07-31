package com.example.moviereviewer.repository.pojo

import android.R
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.moviereviewer.datasources.api.ApiFactory
import com.example.moviereviewer.datasources.database.MovieDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers


object Repository {

    const val API_KEY = "978314745d3ce652ac32e226b079bf48"
    const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/"
    const val SMALL_POSTER_SIZE = "w185"
    const val BIG_POSTER_SIZE = "w780"
    const val METHOD_OF_SORT = "popularity.desc"

    lateinit var contextForDB: Context
    lateinit var db: MovieDatabase
    var compositeDisposable = CompositeDisposable()


    fun loadMovies(lang: String, page: Int, context: Context) {
        db = MovieDatabase.getInstance(context)
        val disposable = ApiFactory.apiService.getMovies(API_KEY, lang, METHOD_OF_SORT, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val movies: List<Movie>? = it?.results
                if (movies != null) {
                    for (m in movies) {
                        m.poster_path_big = BASE_POSTER_URL + BIG_POSTER_SIZE + m.poster_path_small
                        m.poster_path_small =
                            BASE_POSTER_URL + SMALL_POSTER_SIZE + m.poster_path_small
                    }
                    if (page == 1) {
                        deleteAllMovies()
                    }
                    insertMovies(movies)
                    Log.i("checkpage", page.toString())
                } else {
                    Log.i("error", "error")
                }
            }, {
                Log.i("TEST_OF_LOADING_DATA", "Failure: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    private fun insertMovies(movies: List<Movie>) {
        compositeDisposable.add(
            Completable.fromAction { db.movieDao().insertAllMovies(movies) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun deleteAllMovies() {
        compositeDisposable.add(
            Completable.fromAction { db.movieDao().deleteAllMovies() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun dispose() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose()
        }
    }
}