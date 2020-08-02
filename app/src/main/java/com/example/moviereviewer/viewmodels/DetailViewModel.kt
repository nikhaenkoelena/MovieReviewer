package com.example.moviereviewer.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.moviereviewer.datasources.database.MovieDatabase
import com.example.moviereviewer.repository.pojo.Movie
import com.example.moviereviewer.repository.pojo.Repository

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val db = MovieDatabase.getInstance(application)
    val reviews = db.movieDao().getReviews()
    val trailers = db.movieDao().getTrailers()
    lateinit var movieById: LiveData<Movie>

    fun loadMovieById(id: Int) {
        movieById = db.movieDao().loadMovieById(id)
    }

    fun loadReviews(id: Int, lang: String, context: Context?) {
        Repository.loadReviews(id, lang, context)
    }

    fun loadTrailers(id: Int, lang: String, context: Context?) {
        Repository.loadTrailers(id, lang, context)
    }

    fun setFavoriteOption(id: Int, isFavorite: Boolean) {
        Repository.setFavoriteOption(id, isFavorite)
    }

}