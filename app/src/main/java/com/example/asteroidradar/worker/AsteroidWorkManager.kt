package com.example.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradar.data.local.AsteroidDataBase
import com.example.asteroidradar.repo.AsteroidRepository
import retrofit2.HttpException

class AsteroidWorkManager (appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {

   override suspend fun doWork(): Result {
        val database =  AsteroidDataBase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshAsteroidList()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}