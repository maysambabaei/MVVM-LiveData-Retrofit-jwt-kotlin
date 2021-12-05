package com.example.moviesapplication.network

import android.content.Context
import android.content.SharedPreferences
import com.example.moviesapplication.utils.MovieApp
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.moviesapplication.models.register.RefreshTokenModel
import okhttp3.*

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit


class RestClient {

    companion object {
        private const val BASE_URL = "http://moviesapi.ir/"
        private lateinit var mApiServices: ApiServices
        private var mInstance: RestClient? = null
        fun getInstance(): RestClient {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = RestClient()
                }
            }
            return mInstance!!
        }
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(ResponseInterceptor(MovieApp.getAppContext()))
        okHttpClient.connectTimeout(15, TimeUnit.SECONDS)
        okHttpClient.readTimeout(15, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(30, TimeUnit.SECONDS)
        okHttpClient.build()

        okHttpClient.addInterceptor(ResponseInterceptor())
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mApiServices = retrofit.create(ApiServices::class.java)
    }

    fun getApiService() = mApiServices
}