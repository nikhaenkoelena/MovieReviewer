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

    fun loadReviews(id: Int, lang: String, context: Context?) {
        Log.i("checkLang", lang.toString())
        Repository.loadReviews(id, lang, context)
    }
}