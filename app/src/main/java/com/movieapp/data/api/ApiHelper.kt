package com.movieapp.data.api

import com.movieapp.data.model.Movie
import retrofit2.Response

/**
 * Helper class to define functions & which data it's going to return
 */
interface ApiHelper {

    suspend fun getMovies(): Response<List<Movie>>

}