package com.example.moviereviewer.datasources.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviereviewer.repository.pojo.Movie
import com.example.moviereviewer.repository.pojo.Review
import retrofit2.http.DELETE

@Dao
interface MovieDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies : List<Movie>)

    @Query("SELECT * FROM movies_table")
    fun getAllMovies() : LiveData<List<Movie>>

    @Transaction
    @Query("DELETE FROM movies_table")
    fun deleteAllMovies()

    @Query("DELETE FROM reviews_table")
    fun deleteAllReviews()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllReviews(reviews : List<Review>)

    @Query("SELECT * FROM reviews_table")
    fun getReviews() : LiveData<List<Review>>
}