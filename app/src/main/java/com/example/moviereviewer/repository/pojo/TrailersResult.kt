package com.example.moviereviewer.repository.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrailersResult(
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("results")
    @Expose
    var trailers: List<Trailer> = listOf()

)