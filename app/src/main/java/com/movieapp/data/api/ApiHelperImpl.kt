package com.movieapp.data.api

import com.movieapp.data.model.Movie
import retrofit2.Response
import javax.inject.Inject

/**
 * implementation layer to get data from APIs
 *
 * @property apiService
 */
class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getMovies(): Response<List<Movie>> = apiService.getMovies()

}