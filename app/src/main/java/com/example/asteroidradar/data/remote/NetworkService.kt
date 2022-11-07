package com.example.asteroidradar.data.remote

import com.example.asteroidradar.models.PictureOfDay
import com.example.asteroidradar.utils.Constants
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface NetworkService {
    @GET("neo/rest/v1/feed")
    suspend fun getAllAsteroid(@Query("api_key") apiKey:String = Constants.NASA_API_KEY):String
    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey:String = Constants.NASA_API_KEY): PictureOfDay

}

object NetworkFactory {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit by lazy {

        val okHttpClient = OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    }

    private val networkService: NetworkService by lazy {
        retrofit.create(NetworkService::class.java)
    }

    fun getAsteroidApi(): NetworkService {
        return networkService
    }

}

