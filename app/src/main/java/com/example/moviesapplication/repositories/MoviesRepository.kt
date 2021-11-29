package com.example.moviesapplication.repositories

import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.interfaces.NetworkResponseCallback
import com.example.moviesapplication.models.moviesdetail.MoviesDetailModel
import com.example.moviesapplication.models.movieslist.Movies
import com.example.moviesapplication.models.movieslist.MoviesMetadata
import com.example.moviesapplication.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mMovie: MutableLiveData<Movies?> = MutableLiveData<Movies?>()
    private var moviesDetail: MutableLiveData<MoviesDetailModel?> =
        MutableLiveData<MoviesDetailModel?>()
    private lateinit var movieCall: Call<Movies>
    private lateinit var movieDetailCall: Call<MoviesDetailModel>

    fun getMovies(
        callback: NetworkResponseCallback,
        forceFetch: Boolean
    ): MutableLiveData<Movies?> {
        mCallback = callback
        if (mMovie.value != null && !forceFetch) {
            mCallback.onResponseSuccess()
            return mMovie
        }
        movieCall = RestClient.getInstance().getApiService().getMovies(page = 1)
        movieCall.enqueue(object : Callback<Movies> {

            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                mMovie.value = response.body()
                mCallback.onResponseSuccess()
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                mMovie.value = null
                mCallback.onResponseFailure(t)
            }

        })
        return mMovie
    }

    fun getMoviesDetail(
        callback: NetworkResponseCallback,
        forceFetch: Boolean,
        movieId: Int
    ): MutableLiveData<MoviesDetailModel?> {
        mCallback = callback
        if (moviesDetail.value != null && !forceFetch) {
            mCallback.onResponseSuccess()
            return moviesDetail
        }
        movieDetailCall = RestClient.getInstance().getApiService().getMoviesDetail(movieId)
        movieDetailCall.enqueue(object : Callback<MoviesDetailModel> {

            override fun onResponse(
                call: Call<MoviesDetailModel>,
                response: Response<MoviesDetailModel>
            ) {
                moviesDetail.value = response.body()
                mCallback.onResponseSuccess()
            }

            override fun onFailure(call: Call<MoviesDetailModel>, t: Throwable) {
                mMovie.value = null
                mCallback.onResponseFailure(t)
            }

        })
        return moviesDetail
    }

    fun searchMovies(
        callback: NetworkResponseCallback,
        forceFetch: Boolean,
        movieName:String,
        page:Int
    ): MutableLiveData<Movies?> {
        mCallback = callback
        if (mMovie.value != null && !forceFetch) {
            mCallback.onResponseSuccess()
            return mMovie
        }
        movieCall = RestClient.getInstance().getApiService().searchMovies(name = movieName,page = page)
        movieCall.enqueue(object : Callback<Movies> {

            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                mMovie.value = response.body()
                mCallback.onResponseSuccess()
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                mMovie.value = null
                mCallback.onResponseFailure(t)
            }

        })
        return mMovie
    }



    companion object {
        private var mInstance: MoviesRepository? = null
        fun getInstance(): MoviesRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = MoviesRepository()
                }
            }
            return mInstance!!
        }
    }

}