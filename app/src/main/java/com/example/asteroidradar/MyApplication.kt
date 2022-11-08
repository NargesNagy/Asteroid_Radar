package com.example.asteroidradar

import android.app.Application
import android.util.Log
import androidx.work.*
import com.example.asteroidradar.utils.Constants
import com.example.asteroidradar.worker.AsteroidWorkManager
import java.util.concurrent.TimeUnit

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("TAG", "MyApplication onCreate:nnnnnnnnnn ")
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

            //  .setRequiresCharging(true)

        val dailyFetchRequest = PeriodicWorkRequestBuilder<AsteroidWorkManager>(1, TimeUnit.DAYS)
            .setConstraints(constraint)
            .build()
        Log.i("TAG", "MyApplication onCreate:nnnnnnnnnn ")

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(Constants.WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, dailyFetchRequest)

        Log.e("TAG", "MyApplication onCreate: ")

    }
}

