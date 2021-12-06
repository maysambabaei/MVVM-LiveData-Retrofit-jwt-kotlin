package com.example.moviesapplication.models.genres


import com.google.gson.annotations.SerializedName

data class GenresData(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
)