package com.example.moviesapplication.network

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.example.moviesapplication.models.register.RefreshTokenModel
import com.example.moviesapplication.ui.user.LoginActivity
import com.example.moviesapplication.utils.MovieApp
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import java.io.IOException

class ResponseInterceptor : Interceptor {
    private var mApplication: MovieApp
    private var mContext: Context?

    constructor() { 
        mApplication = MovieApp.getInstance()!!
        mContext = null
    }

    constructor(context: Context?) {
        mApplication = MovieApp.getInstance()!!
        mContext = context
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val sharedPreferences: SharedPreferences =
            MovieApp.getAppContext().getSharedPreferences(
                "myPrefs",
                Context.MODE_PRIVATE
            )
        var token = sharedPreferences.getString("PREFS_AUTH_ACCESSES_TOKEN", null)
        val request: Request = chain.request().newBuilder()
            .addHeader("authorization",
                "Bearer $token")
            .addHeader("accept","application/json")
            .build()
        val lResponse: Response = chain.proceed(request)

        when {
            lResponse.code()===200 ->{
                // success
                Log.e("interceptor", "200")

            }
            lResponse.code() === 400 ->{
                var intent=Intent(mContext,LoginActivity::class.java)
                mContext?.startActivity(intent)
            }

            lResponse.code() === 401 -> {
                // unauthorized
                val refreshToken: String =
                    sharedPreferences.getString("AUTH_REFRESH_TOKEN", null).toString()
                //make call
                val mApiServices=RestClient.getInstance().getApiService()
                val call: Call<RefreshTokenModel> =
                    mApiServices.refreshToken("refresh_token", refreshToken)
                val tokenModelResponse: retrofit2.Response<RefreshTokenModel> = call.execute()
                if (tokenModelResponse.isSuccessful) {
                    val editor = sharedPreferences.edit()
                    editor.putString(
                        "PREFS_AUTH_ACCESSES_TOKEN",
                        tokenModelResponse.body()?.accessToken.toString()
                    )
                    editor.putString(
                        "AUTH_REFRESH_TOKEN",
                        tokenModelResponse.body()?.refreshToken.toString()
                    )
                    editor.commit()
                    lResponse.request().newBuilder()
                        .removeHeader("Authorization")
                        .addHeader(
                            "Authorization",
                            "Bearer " + tokenModelResponse.body()?.accessToken
                        )
                        .build()
                } else {
                    null
                }
            }
            
            lResponse.code() === 403 -> {
                // forbidden
                Log.e("interceptor", "403")
            }
            lResponse.code() === 404 -> {
                // endpoint not found
                Log.e("interceptor", "404")
            }
            lResponse.code() === 500 -> {
                // internal server error
                Log.e("interceptor", "500")
            }
            lResponse.code() === 502 -> {
                // bad gateway
                Log.e("interceptor", "502")
            }
            lResponse.code() === 503 -> {
                // service unavailable
                Log.e("interceptor", "503")
            }
            lResponse.code() === 504 -> {
                // gateway timeout
                Log.e("interceptor", "504")
            }
        }
        

        return lResponse
    }
}
