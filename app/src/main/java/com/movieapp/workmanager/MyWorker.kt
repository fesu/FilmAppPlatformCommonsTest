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
                    displayNotification("Data Sync", "Data has been synced successfully.")
                } else {
                    displayNotification("Data Sync", "Data Failed to sync, It will try again after 30 minutes")
                    Log.d("Worker", "Failed to sync data after 30 Minutes")
                }
            }
        }
    }

    private fun displayNotification(title: String, task: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "notif_id",
                "notif_name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notification: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, "notif_id")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(1, notification.build())
    }
}