package com.example.asteroidradar

import android.app.Application
import androidx.work.*
import com.example.asteroidradar.utils.Constants
import com.example.asteroidradar.worker.AsteroidWorkManager
import java.util.concurrent.TimeUnit

class AsteroidRaderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val constraint = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val dailyFetchRequest = PeriodicWorkRequestBuilder<AsteroidWorkManager>(1, TimeUnit.DAYS)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(Constants.WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, dailyFetchRequest)
    }
}
