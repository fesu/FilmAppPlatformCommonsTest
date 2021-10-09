package com.movieapp.data.roomdb

import android.content.Context
import androidx.room.Room
import com.movieapp.data.model.Movie
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This is Singleton Helper class to access methods to save or get data from Local Room DB
 *
 */
@Singleton
class RoomDbHelper @Inject constructor(@ApplicationContext private val context: Context) {

    private val movieDbName: String = "movie_database"

    fun cacheDataToRoomDB(movies: List<Movie>) {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, movieDbName
        ).allowMainThreadQueries().build()

        val movieDao = db.movieDao()

        // First clear older data
        movieDao.clearAll()

        // Add new data to Local DB
//        movieDao.insertAll(movies)
        for (movie in movies) {
            movieDao.insert(movie)
        }
    }

    fun getCachedData(): List<Movie> {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, movieDbName
        ).allowMainThreadQueries().build()

        val movieDao = db.movieDao()

        return movieDao.getAll()
    }


}
