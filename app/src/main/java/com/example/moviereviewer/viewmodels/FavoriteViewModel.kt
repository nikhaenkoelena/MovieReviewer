package com.example.moviereviewer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.moviereviewer.datasources.database.MovieDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        const val IS_FAVORITE = true
    }

    private val db = MovieDatabase.getInstance(application)
    val favoriteMovies = db.movieDao().getFavoriteMovies(IS_FAVORITE)
}