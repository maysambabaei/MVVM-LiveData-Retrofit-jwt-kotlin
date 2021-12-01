package com.example.moviesapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.interfaces.NetworkResponseCallback
import com.example.moviesapplication.models.addmovies.AddMoviesModel
import com.example.moviesapplication.models.addmovies.MovieInput
import com.example.moviesapplication.repositories.MoviesRepository
import com.example.moviesapplication.utils.NetworkHelper
import java.io.File

class AddMoviesViewModel(private val app: Application) : AndroidViewModel(app) {
    private lateinit var addMovie: MutableLiveData<AddMoviesModel?>
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    var mRepository = MoviesRepository.getInstance()

    fun addMovie(forceFetch: Boolean, movieInput: MovieInput): MutableLiveData<AddMoviesModel?> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            addMovie = mRepository.addMovie(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            }, forceFetch, movieInput)
        } else {
            mShowNetworkError.value = true
        }
        return addMovie
    }


}