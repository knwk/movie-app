package com.example.movieapp.model

import io.reactivex.Single
import retrofit2.http.*

interface MovieApi {

    @GET("movie/upcoming")
    fun getMovies(@Query("api_key") key: String): Single<TMDBResponse>

}