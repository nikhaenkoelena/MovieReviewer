package com.example.moviereviewer.datasources.api

import com.example.moviereviewer.repository.pojo.MovieResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") API_KEY: String?,
        @Query("language") language: String?,
        @Query("sort_by") typeOfSort: String?,
        @Query("vote_count.gte") vote: Int,
        @Query("page") page: Int
    ): Observable<MovieResult?>

}