package com.example.moviereviewer.repository.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("page")
    @Expose
    var page: Int = 0,
    @SerializedName("total_results")
    @Expose
    var totalResults: Int = 0,
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0,
    @SerializedName("results")
    @Expose
    var results: MutableList<Movie>? = null
)