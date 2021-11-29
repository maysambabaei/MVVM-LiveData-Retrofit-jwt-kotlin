package com.example.moviesapplication.models.moviesdetail


import com.google.gson.annotations.SerializedName

data class MoviesDetailModel(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("poster")
    var poster: String?,
    @SerializedName("year")
    var year: String?,
    @SerializedName("rated")
    var rated: String?,
    @SerializedName("released")
    var released: String?,
    @SerializedName("runtime")
    var runtime: String?,
    @SerializedName("director")
    var director: String?,
    @SerializedName("writer")
    var writer: String?,
    @SerializedName("actors")
    var actors: String?,
    @SerializedName("plot")
    var plot: String?,
    @SerializedName("country")
    var country: String?,
    @SerializedName("awards")
    var awards: String?,
    @SerializedName("metascore")
    var metascore: String?,
    @SerializedName("imdb_rating")
    var imdbRating: String?,
    @SerializedName("imdb_votes")
    var imdbVotes: String?,
    @SerializedName("imdb_id")
    var imdbId: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("genres")
    var genres: List<String>?,
    @SerializedName("images")
    var images: List<String>?
)