package com.example.moviesapplication.models.movieslist


import com.google.gson.annotations.SerializedName

data class MoviesData(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("year")
    var year: String?,
    @SerializedName("country")
    var country: String?,
    @SerializedName("imdb_rating")
    var imdbrating: String?,
    @SerializedName("poster")
    var poster: String?,
    @SerializedName("genres")
    var genres: List<String>?,
    @SerializedName("images")
    var images: List<String>?
)