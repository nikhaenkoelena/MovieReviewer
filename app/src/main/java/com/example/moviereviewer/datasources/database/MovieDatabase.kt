package com.example.moviereviewer.datasources.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviereviewer.repository.pojo.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        private var db: MovieDatabase? = null
        const val DB_NAME = "movies.db"
        private val LOCK = Any()
    }

    fun getInstance(context: Context): MovieDatabase {
        synchronized(LOCK) {
            db?.let { return it }
            val instance =
                Room.databaseBuilder(context, MovieDatabase::class.java, DB_NAME)
                    .build()
            db = instance
            return instance
        }
    }

    abstract fun movieDao(): MovieDao
}