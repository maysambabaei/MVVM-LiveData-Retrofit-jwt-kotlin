package com.example.moviesapplication.network

import com.example.moviesapplication.models.addmovies.AddMoviesModel
import com.example.moviesapplication.models.login.LoginUserModel
import com.example.moviesapplication.models.moviesdetail.MoviesDetailModel
import com.example.moviesapplication.models.movieslist.Movies
import com.example.moviesapplication.models.user.UserModel
import com.example.moviesapplication.models.register.RegisterUserInput
import com.example.moviesapplication.models.register.RegisterUserModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @GET("api/v1/movies")
    fun getMovies(@Query("page") page: Int): Call<Movies>

    @GET("api/v1/movies/{movie_id}")
    fun getMoviesDetail(@Path("movie_id") id: Int): Call<MoviesDetailModel>

    @GET("api/v1/movies")
    fun searchMovies(@Query("q") name: String, @Query("page") page: Int): Call<Movies>

    @POST("api/v1/movies/multi")
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


    @POST("api/v1/register")
    fun registerUser(@Body registerUserInput: RegisterUserInput): Call<RegisterUserModel>

    @POST("oauth/token")
    fun loginUser(
        @Query("grant_type") grantType: String,
        @Query("username") username: String,
        @Query("password") password: String
    ):Call<LoginUserModel>

    @GET("api/user")
    fun getUser():Call<UserModel>

}