package com.example.moviereviewer.datasources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviereviewer.repository.pojo.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies : List<Movie>)

    @Query("SELECT * FROM movies_table")
    fun getAllMovies() :List<Movie>

    @Query("DELETE FROM movies_table")
    fun deleteAllMovies()
}