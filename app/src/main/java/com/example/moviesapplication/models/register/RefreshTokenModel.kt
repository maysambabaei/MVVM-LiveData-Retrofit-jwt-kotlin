package com.example.moviesapplication.models.register


import com.google.gson.annotations.SerializedName

data class RefreshTokenModel(
    @SerializedName("token_type")
    var tokenType: String?,
    @SerializedName("expires_in")
    var expiresIn: Int?,
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("refresh_token")
    var refreshToken: String?
)