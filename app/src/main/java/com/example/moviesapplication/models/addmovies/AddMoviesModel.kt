package com.example.moviesapplication.models.addmovies


import com.google.gson.annotations.SerializedName

data class AddMoviesModel(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("poster")
    var poster: String?,
    @SerializedName("year")
    var year: Int?,
    @SerializedName("director")
    var director: String?,
    @SerializedName("country")
    var country: String?,
    @SerializedName("imdb_rating")
    var imdbRating: String?,
    @SerializedName("imdb_votes")
    var imdbVotes: String?,
    @SerializedName("imdb_id")
    var imdbId: String?
)