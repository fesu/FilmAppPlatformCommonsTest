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

    @Json(name = "name")
    val name: String? = ""
)