package com.example.moviereviewer.datasources.api

import com.example.moviereviewer.repository.pojo.MovieResult
import com.example.moviereviewer.repository.pojo.ReviewResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") API_KEY: String,
        @Query("language") language: String,
        @Query("sort_by") typeOfSort: String,
        @Query("page") page: Int
    ): Observable<MovieResult>

    @GET("movie/{id}/reviews")
    fun getReviews(
        @Path("id") id: Int,
        @Query("api_key") API_KEY: String?,
        @Query("language") language: String?
    ): Observable<ReviewResult>

}