package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

// ListViewModel class connects Movie (model) and ListFragment (view)
// Exposes LiveData variables and allow ListFragment to observe LiveData and receive data

class ListViewModel(application: Application): AndroidViewModel(application) {

    // LiveData variables (view observes and responds to changes)
    val movies by lazy { MutableLiveData<List<TMDBMovie>>() } // Store movie data
    val loadError by lazy { MutableLiveData<Boolean>() } // True when no response
    val loading by lazy { MutableLiveData<Boolean>() } // Display spinner when true

    // Api call variables
    private val disposable = CompositeDisposable() // Attach to Single object (API Response)
    private val apiService = MovieApiService() // Used to make calls to api
    private val posterBaseUrl = "https://image.tmdb.org/t/p/original/" // Concat to get image
    private val apiKey = "" // TEMPORARY KEY FOR TESTING PURPOSES

    // Data retrieval from backend
    fun refresh() {
        loading.value = true
        getMovies(apiKey)
    }

    // Retrieve movie data
    private fun getMovies(key: String) {
        disposable.add(
            apiService.getMovies(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<TMDBResponse>() {
                    override fun onSuccess(res: TMDBResponse) {
                        res.movieList?.map {
                            it.poster_path = posterBaseUrl + it.poster_path
                        }
                        loadError.value = false
                        movies.value = res.movieList
                        loading.value = false
                        print(res.movieList)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        movies.value = null
                        loadError.value = true
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear() // Dispose link to Single (avoid memory leak)
    }

}