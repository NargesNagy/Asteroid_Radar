package com.example.asteroidradar.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.asteroidradar.models.Asteroid
import com.example.asteroidradar.models.PictureOfDay

@Dao
interface AsteroidDao {
    @Query("select* from Asteroid order by closeApproachDate")
    fun getAllAsteroid(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroid(asteroid: List<Asteroid>): List<Long>

    @Transaction
    fun updateData(asteroid: List<Asteroid>): List<Long> {
        deleteAll()
        return insertAllAsteroid(asteroid)
    }

    @Query("select * from Asteroid where closeApproachDate=:date order by closeApproachDate Asc")
    fun getAsteroidOfTheDay(date: String): LiveData<List<Asteroid>>

    @Query("select * from Asteroid where closeApproachDate between date() and  date('now','+7 days') order by closeApproachDate ASc ")
    fun getAsteroidTheNextWeek(): LiveData<List<Asteroid>>

    @Query("Delete from Asteroid")
    fun deleteAll()

    @Query("select * from PictureOfDay")
    fun getPictureOfDay(): LiveData<PictureOfDay>
}
