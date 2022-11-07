package com.example.asteroidradar.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.asteroidradar.data.local.AsteroidDataBase
import com.example.asteroidradar.data.remote.NetworkFactory
import com.example.asteroidradar.data.remote.parseAsteroidsJsonResult
import com.example.asteroidradar.models.Asteroid
import com.example.asteroidradar.models.PictureOfDay
import com.example.asteroidradar.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val localDataBase: AsteroidDataBase) {

    suspend fun refreshAsteroidList() {
        withContext(Dispatchers.IO) {
            val asteroid = NetworkFactory.getAsteroidApi().getAllAsteroid(Constants.NASA_API_KEY)
            val json = JSONObject(asteroid)
            val data = parseAsteroidsJsonResult(json)
            localDataBase.asteroidDao().updateData(data)

            val response = NetworkFactory.getAsteroidApi().getPictureOfDay(Constants.NASA_API_KEY)
            localDataBase.asteroidDao().insertPictureOfDay(response)

        }
    }

    suspend fun getPictureOfTheDay(): PictureOfDay? {

        return withContext(Dispatchers.IO) {
            val response = NetworkFactory.getAsteroidApi().getPictureOfDay(Constants.NASA_API_KEY)
            localDataBase.asteroidDao().insertPictureOfDay(response)

            return@withContext  response
            //localDataBase.asteroidDao().getPictureOfDay()
        }
    }

    suspend fun getPictureOf(): LiveData<PictureOfDay>? {

        return withContext(Dispatchers.IO) {
            val response = NetworkFactory.getAsteroidApi().getPictureOfDay(Constants.NASA_API_KEY)
            localDataBase.asteroidDao().insertPictureOfDay(response)
            Log.i("TAG", "getPictureOflllllllllll: ${localDataBase.asteroidDao().getPictureOfDay().value}")

            return@withContext  localDataBase.asteroidDao().getPictureOfDay()
            //
        }
    }


    suspend fun getAllAsteroid(): LiveData<List<Asteroid>> {
        return withContext(Dispatchers.IO) {

            return@withContext localDataBase.asteroidDao().getAllAsteroid()
        }

    }

    fun getAsteroidOfToday(): LiveData<List<Asteroid>> {
        Log.i("TAG", "getAsteroidOfToday: ${Constants.getCurrentDate()}")
        return localDataBase.asteroidDao().getAsteroidOfTheDay(Constants.getCurrentDate())
    }

    fun getAsteroidONextWeek(): LiveData<List<Asteroid>> {
        return localDataBase.asteroidDao().getAsteroidTheNextWeek()
    }


}
