package com.example.moviesapplication.network

import android.content.Context
import android.content.SharedPreferences
import com.example.moviesapplication.utils.MovieApp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder

import com.google.gson.Gson




class RestClient private constructor() {
    private var token: String? = ""

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
        val sharedPreferences: SharedPreferences =
            MovieApp.getAppContext().getSharedPreferences(
                "myPrefs",
                Context.MODE_PRIVATE
            )
        token = sharedPreferences.getString("PREFS_AUTH_ACCESSES_TOKEN", null)
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(15, TimeUnit.SECONDS)
        okHttpClient.readTimeout(15, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(30, TimeUnit.SECONDS)
        okHttpClient.build()
        if (token.toString().isNotEmpty() && token != null) {
            okHttpClient.interceptors().add(Interceptor { chain ->
                val original: Request = chain.request()
                val builder: Request.Builder = original.newBuilder()
                builder.header(
                    "authorization",
                    "Bearer $token"
                )
                val request: Request = builder.method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            })
        }
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mApiServices = retrofit.create(ApiServices::class.java)
    }

    fun getApiService() = mApiServices

    class HeaderInterceptor(private val authorization: String) : Interceptor {


        override fun intercept(chain: Interceptor.Chain): Response = chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("authorization", "Bearer $authorization")
                    .addHeader("accept", "application/json")
                    .build()
            )
            return chain.proceed(request())
        }

    }


}