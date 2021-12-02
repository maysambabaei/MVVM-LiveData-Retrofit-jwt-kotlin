package com.example.moviesapplication.models.register


import com.google.gson.annotations.SerializedName

data class RegisterUserInput(
    @SerializedName("email")
    var email: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("name")
    var name: String?
){
    constructor():this(
        "",
        "",
        ""
    )
}