package com.example.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.asteroidradar.data.local.AsteroidDataBase
import com.example.asteroidradar.models.Asteroid
import com.example.asteroidradar.models.PictureOfDay
import com.example.asteroidradar.repo.AsteroidRepository
import kotlinx.coroutines.launch


class MainViewModel (val repo: AsteroidRepository) : ViewModel() {

    var asteroidResult: LiveData<List<Asteroid>> = MutableLiveData(emptyList())

    private val _pictureResult = MutableLiveData<PictureOfDay>()
    val pictureResult: LiveData<PictureOfDay> = _pictureResult

    private val _asteroidFilter = MutableLiveData<AsteroidFilter>()
    val asteroidFilter: LiveData<AsteroidFilter>
        get() = _asteroidFilter


    init {
        viewModelScope.launch {
            repo.refreshAsteroidList()
            _pictureResult.value = repo.getPictureOfTheDay()
            getSavedAsteroid()
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
        asteroidResult = repo.getAllAsteroid()
        _asteroidFilter.value = AsteroidFilter.SAVED_ASTEROID
    }

}


 /*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //private val database = getDatabase(application)
    val dataBase: AsteroidDataBase = AsteroidDataBase.getInstance(application)
    private val asteroidRepository = AsteroidRepository(dataBase)

    private val _pictureOfDay = MutableLiveData<PictureOfDay?>()
    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfDay

    private val _navigateToDetailAsteroid = MutableLiveData<Asteroid?>()
    val navigateToDetailAsteroid: LiveData<Asteroid?>
        get() = _navigateToDetailAsteroid

    fun doneNavigated() {
        _navigateToDetailAsteroid.value = null
    }


    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    init {
        todayClicked()
        viewModelScope.launch {
            asteroidRepository.getAllAsteroid()
            refreshPictureOfDay()
        }
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetailAsteroid.value = asteroid
    }

    private suspend fun refreshPictureOfDay()  {

        _pictureOfDay.value = asteroidRepository.getPictureOfTheDay()

    }



    fun todayClicked() {
//        viewModelScope.launch {
//            dataBase.asteroidDao().getAllAsteroid()
//                .observe() { asteroids ->
//                    _asteroids.value = asteroids
//                }
//        }
    }

    fun nextWeekClicked() {
//        viewModelScope.launch {
//            dataBase.asteroidDao.getAsteroids(seventhDay(), seventhDay())
//                .collect { asteroids ->
//                    _asteroids.value = asteroids
//                }
//        }
    }

    fun savedClicked() {
//        viewModelScope.launch {
//            dataBase.asteroidDao.getAllAsteroids().collect { asteroids ->
//                _asteroids.value = asteroids
//            }
//        }
    }

}



 */