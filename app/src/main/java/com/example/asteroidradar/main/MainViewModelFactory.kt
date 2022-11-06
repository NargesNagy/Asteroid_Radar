package com.example.asteroidradar.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.asteroidradar.data.local.AsteroidDataBase
import com.example.asteroidradar.repo.AsteroidRepository

class MainViewModelFactory (private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val dataBase: AsteroidDataBase = AsteroidDataBase.getInstance(context)
            val repo = AsteroidRepository(dataBase)
            return MainViewModel(repo) as T
        } else {
            throw IllegalArgumentException("View Model class Not found")

        }

    }
}


