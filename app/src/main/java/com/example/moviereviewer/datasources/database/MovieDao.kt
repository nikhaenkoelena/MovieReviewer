package com.example.moviereviewer.datasources.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviereviewer.repository.pojo.Movie
import com.example.moviereviewer.repository.pojo.Review
import com.example.moviereviewer.repository.pojo.Trailer
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface MovieDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies : List<Movie>)

    @Query("SELECT * FROM movies_table")
    fun getAllMovies() : LiveData<MutableList<Movie>>

    @Transaction
    @Query("DELETE FROM movies_table")
    fun deleteAllMovies()

    @Query("SELECT *FROM movies_table WHERE id ==:id")
    fun loadMovieById(id: Int) : LiveData<Movie>

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

    @Query("UPDATE movies_table SET isFavorite =:isFavorite WHERE id ==:id")
    fun setFavoriteOption(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM movies_table WHERE isFavorite =:isFavorite")
    fun getFavoriteMovies(isFavorite: Boolean) : LiveData<List<Movie>>

    @Transaction
    @Query("SELECT * FROM movies_table")
    fun getMoviesForIsFavorite() : Observable<MutableList<Movie>>
}