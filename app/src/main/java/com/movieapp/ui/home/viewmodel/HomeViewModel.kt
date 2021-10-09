package com.movieapp.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.data.model.Movie
import com.movieapp.data.repository.MainRepository
import com.movieapp.data.roomdb.RoomDbHelper
import com.movieapp.utils.NetworkHelper
import com.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Home ViewModel MVVM component to get data from API & pass it to the UI layer.
 *
 * @property mainRepository
 * @property networkHelper
 * @property roomDbHelper
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val roomDbHelper: RoomDbHelper
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>>
        get() = _movies

    init {
        getAllMovieList()
    }

    /**
     * Call All Movie details API using Kotlin Coroutine
     */
    private fun getAllMovieList() {
        viewModelScope.launch {
            _movies.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getMovies().let {
                    if (it.isSuccessful) {
                        _movies.postValue(Resource.success(it.body()))
                        it.body()?.let { it1 -> roomDbHelper.cacheDataToRoomDB(it1) }
                    } else _movies.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else {
                // If no internet than get data from Local Room DB
                val localData:List<Movie> = roomDbHelper.getCachedData()

                if (localData.isNotEmpty()){
                    _movies.postValue(Resource.success(localData))
                }
                else{
                    // If even local db is empty than show No Internet message
                    _movies.postValue(Resource.error("No internet connection", null))
                }
            }
        }
    }


}