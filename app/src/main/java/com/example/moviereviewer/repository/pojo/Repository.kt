package com.example.moviereviewer.repository.pojo

import android.content.Context
import android.util.Log
import com.example.moviereviewer.datasources.api.ApiFactory
import com.example.moviereviewer.datasources.database.MovieDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber


object Repository {

    private const val BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v="
    private const val API_KEY = "978314745d3ce652ac32e226b079bf48"
    private const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/"
    private const val SMALL_POSTER_SIZE = "w185"
    private const val BIG_POSTER_SIZE = "w780"
    private const val METHOD_OF_SORT = "popularity.desc"

    lateinit var db: MovieDatabase
    var compositeDisposable = CompositeDisposable()


    fun loadMovies(lang: String, page: Int, context: Context) {
        db = MovieDatabase.getInstance(context)
        val disposable = ApiFactory.apiService.getMovies(API_KEY, lang, METHOD_OF_SORT, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                var movies: MutableList<Movie>? = it?.results
                if (movies != null) {
                    for (m in movies) {
                        m.poster_path_big = BASE_POSTER_URL + BIG_POSTER_SIZE + m.poster_path_small
                        m.poster_path_small =
                            BASE_POSTER_URL + SMALL_POSTER_SIZE + m.poster_path_small
                    }
                    movies = checkIsFavorite(movies)
                    if (page == 1) {
                        deleteAllMovies()
                    }
                    insertMovies(movies)
                } else {
                    Log.d("TEST_OF_LOADING_DATA", "error")
                }
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Failure: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    private fun checkIsFavorite(movies: MutableList<Movie>): MutableList<Movie> {
        compositeDisposable.add(db.movieDao().getMoviesForIsFavorite()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                for (m in movies) {
                    for (i in it) {
                        if (i.id == m.id) {
                            m.isFavorite = i.isFavorite
                            break
                        } else {
                            continue
                        }
                    }
                }
                val twoCopy: MutableList<Movie> = it
                twoCopy.removeAll(movies)
                movies.addAll(twoCopy)
            })
        return movies
    }

    private fun insertMovies(movies: List<Movie>) {
        compositeDisposable.add(
            Completable.fromAction { db.movieDao().insertAllMovies(movies) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    private fun deleteAllMovies() {
        compositeDisposable.add(
            Completable.fromAction { db.movieDao().deleteAllMovies() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun loadReviews(id: Int, lang: String, context: Context?) {
        val disposables = ApiFactory.apiService.getReviews(id, API_KEY, lang)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val reviews: List<Review>? = it?.reviews
                deleteAllReviews()
                if (reviews != null)
                    insertAllReviews(reviews)
            }, { Log.d("TEST_OF_LOADING_REVIEWS", "Failure: ${it.message}") })
        compositeDisposable.add(disposables)
    }

    private fun deleteAllReviews() {
        compositeDisposable.add(
            Completable.fromAction { db.movieDao().deleteAllReviews() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun insertAllReviews(reviews: List<Review>) {
        compositeDisposable.add(
            Completable.fromAction { db.movieDao().insertAllReviews(reviews) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun loadTrailers(id: Int, lang: String, context: Context?) {
        val disposables = ApiFactory.apiService.getTrailers(id, API_KEY, lang)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val trailers: List<Trailer>? = it?.trailers
                if (trailers != null)
                    for (t in trailers) {
                        t.key = BASE_YOUTUBE_URL + t.key
                    }
                deleteAllTrailers()
                if (trailers != null)
                    insertAllTrailers(trailers)
            }, { Log.d("TEST_OF_LOADING_TRAILERS", "Failure: ${it.message}") })
        compositeDisposable.add(disposables)
    }

    private fun deleteAllTrailers() {
        compositeDisposable.add(
            Completable.fromAction { db.movieDao().deleteAllTrailers() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun insertAllTrailers(trailers: List<Trailer>) {
        compositeDisposable.add(
            Completable.fromAction { db.movieDao().insertAllTrailers(trailers) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun setFavoriteOption(id: Int, isFavorite: Boolean) {
        compositeDisposable.add(
            Completable.fromAction {
                db.movieDao().setFavoriteOption(id, isFavorite)
            }
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