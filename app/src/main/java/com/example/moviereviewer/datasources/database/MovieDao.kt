package com.example.moviereviewer.datasources.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviereviewer.repository.pojo.Movie
import com.example.moviereviewer.repository.pojo.Review
import com.example.moviereviewer.repository.pojo.Trailer
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

    @Query("DELETE FROM trailers_table")
    fun deleteAllTrailers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTrailers(reviews : List<Trailer>)

    @Query("SELECT * FROM trailers_table")
    fun getTrailers() : LiveData<List<Trailer>>
}