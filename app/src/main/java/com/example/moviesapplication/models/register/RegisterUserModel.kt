package com.example.moviesapplication.models.register


import com.google.gson.annotations.SerializedName

data class RegisterUserModel(
    @SerializedName("name")
    var name: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?
)