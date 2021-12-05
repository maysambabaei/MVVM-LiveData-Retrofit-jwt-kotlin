package com.example.moviesapplication.repositories

import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.interfaces.NetworkResponseCallback
import com.example.moviesapplication.models.addmovies.AddMoviesModel
import com.example.moviesapplication.models.addmovies.MovieInput
import com.example.moviesapplication.models.genres.GenresData
import com.example.moviesapplication.models.moviesdetail.MoviesDetailModel
import com.example.moviesapplication.models.movieslist.Movies
import com.example.moviesapplication.network.RestClient
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviesRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mMovie: MutableLiveData<Movies?> = MutableLiveData<Movies?>()
    private var moviesDetail: MutableLiveData<MoviesDetailModel?> =
        MutableLiveData<MoviesDetailModel?>()
    private var addMovie: MutableLiveData<AddMoviesModel?> = MutableLiveData<AddMoviesModel?>()
    private var genres: MutableLiveData<List<GenresData?>> = MutableLiveData<List<GenresData?>>()
    private lateinit var movieCall: Call<Movies>
    private lateinit var movieDetailCall: Call<MoviesDetailModel>
    private lateinit var addMovieCall: Call<AddMoviesModel>
    private lateinit var getGenresCall: Call<List<GenresData?>>

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
        movieName: String,
        page: Int
    ): MutableLiveData<Movies?> {
        mCallback = callback
        if (mMovie.value != null && !forceFetch) {
            mCallback.onResponseSuccess()
            return mMovie
        }
        movieCall =
            RestClient.getInstance().getApiService().searchMovies(name = movieName, page = page)
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


    fun addMovie(
        callback: NetworkResponseCallback,
        forceFetch: Boolean,
        movieInput: MovieInput
    ): MutableLiveData<AddMoviesModel?> {
        mCallback = callback
        if (addMovie.value != null && !forceFetch) {
            mCallback.onResponseSuccess()
            return addMovie
        }
        val file: RequestBody = RequestBody.create(
            MediaType.parse("image/*"),
            movieInput.poster
        )

        addMovieCall = RestClient.getInstance().getApiService()
            .addMovie(
                movieInput.title,
                movieInput.imdbId,
                movieInput.country,
                movieInput.year,
                movieInput.director.toString(),
                movieInput.imdbRating.toString(),
                movieInput.imdbVotes.toString(),
                file
            )
        addMovieCall.enqueue(object : Callback<AddMoviesModel> {
            override fun onResponse(
                call: Call<AddMoviesModel>,
                response: Response<AddMoviesModel>
            ) {
                addMovie.value = response.body()
                mCallback.onResponseSuccess()
            }

            override fun onFailure(call: Call<AddMoviesModel>, t: Throwable) {
                addMovie.value = null
                mCallback.onResponseFailure(t)
            }

        })
        return addMovie
    }

    fun getGenres(
        callback: NetworkResponseCallback
    ): MutableLiveData<List<GenresData?>> {
        mCallback = callback
        if (genres.value != null) {
            mCallback.onResponseSuccess()
            return genres
        }
        getGenresCall = RestClient.getInstance().getApiService().getGenres()
        getGenresCall.enqueue(object : Callback<List<GenresData?>?> {
            override fun onResponse(
                call: Call<List<GenresData?>?>,
                response: Response<List<GenresData?>?>
            ) {
                genres.value = response.body()
                mCallback.onResponseSuccess()
            }

            override fun onFailure(call: Call<List<GenresData?>?>, t: Throwable) {
                genres.value = null
                mCallback.onResponseFailure(t)
            }

        })
        return genres
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