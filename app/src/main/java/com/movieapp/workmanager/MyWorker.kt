package com.movieapp.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.movieapp.R
import com.movieapp.data.repository.MainRepository
import com.movieapp.data.roomdb.RoomDbHelper
import com.movieapp.utils.NetworkHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * WorkManager class to sync data from API to local Room DB
 *
 * @constructor
 * TODO
 *
 * @param context
 * @param workerParams
 */
@HiltWorker
class MyWorker @AssistedInject constructor(
    @Assisted context: Context, @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @Inject lateinit var networkHelper: NetworkHelper
    @Inject lateinit var roomDbHelper:RoomDbHelper
    @Inject lateinit var mainRepository:MainRepository

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            /**
             * This method will be called every 30 minutes whenever Work Manager is executed
             */
            syncDataFromServer()
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

    private suspend fun syncDataFromServer() {
        if (networkHelper.isNetworkConnected()) {
            mainRepository.getMovies().let {
                if (it.isSuccessful) {
                    it.body()?.let { it1 -> roomDbHelper.cacheDataToRoomDB(it1) }
                } else {
                    Log.d("Worker", "Failed to sync data after 30 Minutes")
                }
            }
        }
    }
}