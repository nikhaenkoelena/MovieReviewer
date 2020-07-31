package com.example.moviereviewer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.moviereviewer.datasources.database.MovieDatabase
import com.example.moviereviewer.repository.pojo.Repository
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = MovieDatabase.getInstance(application)
    val movies = db.movieDao().getAllMovies()

    fun loadMovies(lang: String, page: Int) {
        Repository.loadMovies(lang, page, getApplication())
    }

    override fun onCleared() {
        super.onCleared()
        Repository.dispose()
    }
}