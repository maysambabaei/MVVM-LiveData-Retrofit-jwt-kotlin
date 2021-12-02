package com.example.moviesapplication.models.login


import com.google.gson.annotations.SerializedName

data class LoginUserModel(
    @SerializedName("token_type")
    var tokenType: String?,
    @SerializedName("expires_in")
    var expiresIn: Int?,
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("refresh_token")
    var refreshToken: String?
)