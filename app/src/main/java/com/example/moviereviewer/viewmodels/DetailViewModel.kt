package com.example.moviereviewer.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.moviereviewer.datasources.database.MovieDatabase
import com.example.moviereviewer.repository.pojo.Repository

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val db = MovieDatabase.getInstance(application)
    val reviews = db.movieDao().getReviews()
    val trailers = db.movieDao().getTrailers()

    fun loadReviews(id: Int, lang: String, context: Context?) {
        Repository.loadReviews(id, lang, context)
    }

    fun loadTrailers(id: Int, lang: String, context: Context?) {
        Repository.loadTrailers(id, lang, context)
    }
}