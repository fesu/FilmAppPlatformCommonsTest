package com.movieapp.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Data Class for All Movie details, This class will be used for both getting data from API & also
 * as Data Table for Local Room DB
 *
 * @property id
 * @property title
 * @property image
 * @property movie_banner
 * @property description
 * @property rt_score
 * @property director
 * @property producer
 * @property release_date
 * @property running_time
 */
@Entity(tableName="movie")
data class Movie(
    @Json(name = "id")
    @PrimaryKey @NonNull var id: String = "",

    @Json(name = "title")
    @ColumnInfo(name = "title") var title: String? = "",

    @Json(name = "image")
    @ColumnInfo(name = "image") var image: String? = "",

    @Json(name = "movie_banner")
    @ColumnInfo(name = "movie_banner") var movie_banner: String? = "",

    @Json(name = "description")
    @ColumnInfo(name = "description") var description: String? = "",

    @Json(name = "rt_score")
    @ColumnInfo(name = "rt_score") var rt_score: String? = "",

    @Json(name = "director")
    @ColumnInfo(name = "director") var director: String? = "",

    @Json(name = "producer")
    @ColumnInfo(name = "producer") var producer: String? = "",

    @Json(name = "release_date")
    @ColumnInfo(name = "release_date") var release_date: String? = "",

    @Json(name = "running_time")
    @ColumnInfo(name = "running_time") var running_time: String? = ""
)