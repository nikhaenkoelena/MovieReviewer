package com.example.moviereviewer.repository.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReviewResult(
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("page")
    @Expose
    var page: Int = 0,
    @SerializedName("results")
    @Expose
    var reviews: List<Review> = listOf(),
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0,
    @SerializedName("total_results")
    @Expose
    var totalReviews: Int = 0
)