package com.example.movieapp.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiService {

    private val BASE_URL = "https://api.themoviedb.org/3/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Gson converts response to model class
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // Converts and returns correct type of Observable
        .build()
        .create(MovieApi::class.java) // Pass interface to build service on (we defined the controls in MovieApi.kt)

    fun getMovies(key: String): Single<TMDBResponse> {
        return api.getMovies(key)
    }

}