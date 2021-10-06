package com.movieapp.data.repository

import com.movieapp.data.api.ApiHelper
import javax.inject.Inject

/**
 * Here we will pass ApiHelper in the constructor of the repository
 *
 * @property apiHelper
 */
class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getMovies() =  apiHelper.getMovies()

}