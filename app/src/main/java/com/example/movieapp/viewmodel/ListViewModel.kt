package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.model.Movie

// ListViewModel class connects Movie (model) and ListFragment (view)
// Exposes LiveData variables and allow ListFragment to observe LiveData and receive data

class ListViewModel(application: Application): AndroidViewModel(application) {

    // LiveData variables
    val movies by lazy { MutableLiveData<List<Movie>>() } // store movie data
    val loadError by lazy { MutableLiveData<Boolean>() } // true when no response
    val loading by lazy { MutableLiveData<Boolean>() } // display spinner when true

    // Data retrieval from backend
    fun refresh() {
        getMovies()
    }

    // Retrieve movie data ()
    private fun getMovies() {
        // Create mock data
        val m1 = Movie(name="Movie 1")
        val m2 = Movie(name="Movie 2")
        val m3 = Movie(name="Movie 3")
        val m4 = Movie(name="Movie 4")
        val m5 = Movie(name="Movie 5")
        val m6 = Movie(name="Movie 5")
        val movieList = arrayListOf(m1, m2, m3, m4, m5, m6)
        // Set LiveData variables s.t. observers can get updated data
        movies.value = movieList
        loadError.value = false
        loading.value = false
    }
}