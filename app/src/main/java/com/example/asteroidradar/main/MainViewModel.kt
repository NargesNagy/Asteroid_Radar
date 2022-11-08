package com.example.asteroidradar.main

import android.util.Log
import androidx.lifecycle.*
import com.example.asteroidradar.models.Asteroid
import com.example.asteroidradar.models.PictureOfDay
import com.example.asteroidradar.repo.AsteroidRepository
import kotlinx.coroutines.launch


class MainViewModel (val repo: AsteroidRepository) : ViewModel() {

    var asteroidResult: LiveData<List<Asteroid>> = MutableLiveData(emptyList())

  //  private val _pictureResult = MutableLiveData<PictureOfDay>()
  //  val pictureResult: LiveData<PictureOfDay> = _pictureResult

    private val _picture = MutableLiveData<LiveData<PictureOfDay>>()
    val pictureOfTheDay: MutableLiveData<LiveData<PictureOfDay>> = _picture


    private val _asteroidFilter = MutableLiveData<AsteroidFilter>()
    val asteroidFilter: LiveData<AsteroidFilter>
        get() = _asteroidFilter


/*
    fun get(){
        viewModelScope.launch {
            repo.refreshAsteroidList()

        }
    }

*/
    init {
    viewModelScope.launch {

    /*    try {
            repo.refreshAsteroidList()

        } catch (e : Exception ) {
            Log.e("TAG", "Fatal Exceptionnnnnnnnnnnn $e ");
        }
*/
        _picture.value = repo.getPictureOf()
        getSavedAsteroid()

        Log.i("TAG", "bbbbbbbbbbbbbbbbbbbbbbb: ${getSavedAsteroid()}")

    }

    }

    fun getAsteroidONextWeek() = viewModelScope.launch {
        asteroidResult = repo.getAsteroidONextWeek()
        _asteroidFilter.value = AsteroidFilter.WEEK_ASTEROID

       }

    fun getAsteroidOfToday() = viewModelScope.launch {

            asteroidResult = repo.getAsteroidOfToday()
            Log.i("TAG", "getAsteroidOfTodayyyyyyy: ${asteroidResult.value}")
            _asteroidFilter.value = AsteroidFilter.TODAY_ASTEROID



    }

    fun getSavedAsteroid() = viewModelScope.launch {
        try{
            asteroidResult = repo.getAllAsteroid()
            _asteroidFilter.value = AsteroidFilter.SAVED_ASTEROID

        } catch (e : Exception ) {
        Log.e("TAG", "Fatal Exceptionnnnnnnnnnnnrrrrrrrrrrrrrrrrr $e ");
    }



          }

}

