package com.movieapp.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movieapp.data.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}