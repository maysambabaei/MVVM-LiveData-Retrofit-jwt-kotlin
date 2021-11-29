package com.example.moviesapplication.models.movieslist


import com.google.gson.annotations.SerializedName

data class MoviesMetadata(
    @SerializedName("current_page")
    var currentPage: Int?,
    @SerializedName("per_page")
    var perPage: Int?,
    @SerializedName("page_count")
    var pageCount: Int?,
    @SerializedName("total_count")
    var totalCount: Int?
)