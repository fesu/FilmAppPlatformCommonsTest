package com.movieapp.data.model

import com.squareup.moshi.Json

/**
 * Data Class for All Movie details
 *
 * @property id
 */
data class Movie(
    @Json(name = "id")
    val id: String? = "",

    @Json(name = "title")
    val title: String? = "",

    @Json(name = "image")
    val image: String? = "",

    @Json(name = "movie_banner")
    val movie_banner: String? = "",

    @Json(name = "description")
    val description: String? = "",

    @Json(name = "rt_score")
    val rt_score: String? = "",

    @Json(name = "director")
    val director: String? = "",

    @Json(name = "producer")
    val producer: String? = "",

    @Json(name = "release_date")
    val release_date: String? = "",

    @Json(name = "running_time")
    val running_time: String? = ""
)