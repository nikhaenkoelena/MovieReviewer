package com.example.moviereviewer.repository.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("uniqId")
    @Expose
    val uniqId: Int,
    @SerializedName("id")
    @Expose
    val id: Int = 0,
    @SerializedName("vote_count")
    @Expose
    val vote_count: Int = 0,
    @SerializedName("vote_average")
    @Expose
    val vote_average: Double = 0.0,
    @SerializedName("title")
    @Expose
    val title: String = "",
    @SerializedName("original_title")
    @Expose
    val original_title: String = "",
    @SerializedName("poster_path_big")
    @Expose
    val poster_path_big: String = "",
    @SerializedName("poster_path")
    @Expose
    val poster_path_small: String = "",
    @SerializedName("backdrop_path")
    @Expose
    val backdrop_path: String = "",
    @SerializedName("overview")
    @Expose
    val overview: String = "",
    @SerializedName("release_date")
    @Expose
    val release_date: String = ""
)