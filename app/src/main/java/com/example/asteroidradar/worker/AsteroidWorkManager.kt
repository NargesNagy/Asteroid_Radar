package com.example.asteroidradar.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradar.data.local.AsteroidDataBase
import com.example.asteroidradar.repo.AsteroidRepository
import retrofit2.HttpException

class AsteroidWorkManager (appContext: Context, params: WorkerParameters)
    : CoroutineWorker(appContext, params) {

   override suspend fun doWork(): Result {
       Log.i("TAG", "doWork: ")

       val database =  AsteroidDataBase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)
       Log.i("TAG", "doWork: ")
        return try {
            Log.i("TAG", "doWork: ")
            repository.refreshAsteroidList()
            Log.i("TAG", "doWork: ")
            Result.success()
        } catch (e: HttpException) {
            Log.i("TAG", "doWork: eerrrorrrrrrrrrrrrrrrrrrr $e ")
            Result.retry()
        }
    }
}

