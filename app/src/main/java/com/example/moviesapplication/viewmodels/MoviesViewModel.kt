package com.example.moviesapplication.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.interfaces.NetworkResponseCallback
import com.example.moviesapplication.models.genres.GenresData
import com.example.moviesapplication.models.movieslist.Movies
import com.example.moviesapplication.repositories.MoviesRepository
import com.example.moviesapplication.utils.NetworkHelper

class MoviesViewModel(private val app: Application) : AndroidViewModel(app) {

    private lateinit var movies: MutableLiveData<Movies?>
    private lateinit var genresMovies: MutableLiveData<Movies?>
    private lateinit var genres: MutableLiveData<List<GenresData?>?>
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    var mRepository = MoviesRepository.getInstance()

    fun getMovies(forceFetch: Boolean): MutableLiveData<Movies?> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            movies = mRepository.getMovies(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            }, forceFetch)
        } else {
            mShowNetworkError.value = true
        }
        return movies
    }

    fun searchMovies(forceFetch: Boolean, movieName: String, page: Int): MutableLiveData<Movies?> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            movies = mRepository.searchMovies(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            }, forceFetch, movieName, page)
        } else {
            mShowNetworkError.value = true
        }
        return movies
    }

    fun getGenres(): MutableLiveData<List<GenresData?>?> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            genres = mRepository.getGenres(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            })
        } else {
            mShowNetworkError.value = true
        }
        return genres
    }

    fun getGenresMovie(forceFetch:Boolean,genresId:Int,page: Int):MutableLiveData<Movies?>{
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            genresMovies = mRepository.getGenresMovies(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            },forceFetch,genresId,page)
        } else {
            mShowNetworkError.value = true
        }
        return genresMovies
    }

    fun onRefreshClicked(view: View) {
        getMovies(true)
    }


}