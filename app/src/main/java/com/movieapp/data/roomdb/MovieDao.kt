package com.movieapp.data.roomdb

import androidx.room.*
import com.movieapp.data.model.Movie

/**
 * This is Data Access Object class to call custom queries to get Data from Room DB
 *
 */
@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie WHERE id IN (:Ids)")
    fun loadAllByIds(Ids: IntArray): List<Movie>

    @Query("SELECT * FROM movie WHERE title LIKE :title LIMIT 1")
    fun findByName(title: String): Movie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg movie: Movie)

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: List<Movie>)*/

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie")
    fun clearAll()
}