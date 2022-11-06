package com.example.asteroidradar.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.asteroidradar.data.local.AsteroidDataBase
import com.example.asteroidradar.data.remote.RetrofitFactory
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
            val asteroid = RetrofitFactory.getAsteroidApi().getAllAsteroid(Constants.NASA_API_KEY)
            val json = JSONObject(asteroid)
            val data = parseAsteroidsJsonResult(json)
            localDataBase.asteroidDao().updateData(data)
        }
    }

    suspend fun getPictureOfTheDay(): PictureOfDay? {

        return withContext(Dispatchers.IO) {
            val response =
                RetrofitFactory.getAsteroidApi().getPictureOfDay(Constants.NASA_API_KEY)

            return@withContext response
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
