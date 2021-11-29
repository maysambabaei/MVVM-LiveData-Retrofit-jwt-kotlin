package com.example.moviesapplication.network

import com.example.moviesapplication.models.moviesdetail.MoviesDetailModel
import com.example.moviesapplication.models.movieslist.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("movies")
    fun getMovies(@Query("page") page: Int): Call<Movies>

    @GET("movies/{movie_id}")
    fun getMoviesDetail(@Path("movie_id") id: Int): Call<MoviesDetailModel>

    @GET("movies")
    fun searchMovies(@Query("q") name: String, @Query("page") page: Int): Call<Movies>

}