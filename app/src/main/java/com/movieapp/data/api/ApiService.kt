package com.movieapp.data.api

import com.movieapp.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET

/**
 * This class is used to define API Calls
 */
interface ApiService {

    @GET("films")
    suspend fun getMovies(): Response<List<Movie>>

}