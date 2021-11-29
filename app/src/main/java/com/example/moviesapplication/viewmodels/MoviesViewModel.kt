package com.example.moviesapplication.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.interfaces.NetworkResponseCallback
import com.example.moviesapplication.models.movieslist.Movies
import com.example.moviesapplication.repositories.MoviesRepository
import com.example.moviesapplication.utils.NetworkHelper

class MoviesViewModel(private val app:Application):AndroidViewModel(app) {

    private lateinit var movies: MutableLiveData<Movies?>
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

    fun searchMovies(forceFetch: Boolean,movieName:String,page:Int): MutableLiveData<Movies?> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            movies = mRepository.searchMovies(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            }, forceFetch,movieName,page)
        } else {
            mShowNetworkError.value = true
        }
        return movies
    }



    fun onRefreshClicked(view: View) {
        getMovies(true)
    }


}