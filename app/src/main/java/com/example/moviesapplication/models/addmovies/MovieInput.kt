package com.example.moviesapplication.models.addmovies

import com.google.gson.annotations.SerializedName
import java.io.File

data class MovieInput(
    @SerializedName("title")
    var title: String,
    @SerializedName("imdb_id")
    var imdbId: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("year")
    var year: Int,
    @SerializedName("director")
    var director: String?,
    @SerializedName("imdb_rating")
    var imdbRating: String?,
    @SerializedName("imdb_votes")
    var imdbVotes: String?,
    @SerializedName("poster")
    var poster: File?
){
    constructor():this("","","",0,"","","",null)
}