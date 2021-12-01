package com.example.moviesapplication.network

import com.example.moviesapplication.models.addmovies.AddMoviesModel
import com.example.moviesapplication.models.addmovies.MovieInput
import com.example.moviesapplication.models.moviesdetail.MoviesDetailModel
import com.example.moviesapplication.models.movieslist.Movies
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @GET("movies")
    fun getMovies(@Query("page") page: Int): Call<Movies>

    @GET("movies/{movie_id}")
    fun getMoviesDetail(@Path("movie_id") id: Int): Call<MoviesDetailModel>

    @GET("movies")
    fun searchMovies(@Query("q") name: String, @Query("page") page: Int): Call<Movies>

    @POST("movies/multi")
    @Multipart
    fun addMovie(
        @Part("title") title: String,
        @Part("imdb_id") imdbId: String,
        @Part("country") country: String,
        @Part("year") year: Int,
        @Part("director") director: String,
        @Part("imdb_rating") imdbRating: String,
        @Part("imdb_votes") imdbVotes: String,
        @Part("poster") poster: RequestBody,
    ): Call<AddMoviesModel>

}