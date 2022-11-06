package com.example.asteroidradar.main

import com.example.asteroidradar.models.Asteroid

interface OnItemClicked {
    fun onItemClicked(asteroid: Asteroid)
}