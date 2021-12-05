package com.example.moviesapplication.utils

import android.app.Application
import android.content.Context

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


    companion object {
        private var movieApp: MovieApp? = null
        lateinit var context: Context
        fun getAppContext(): Context {
            return context
        }
        fun getInstance(): MovieApp? {
            if (movieApp == null) {
                movieApp = MovieApp()
            }
            return movieApp
        }
    }
}