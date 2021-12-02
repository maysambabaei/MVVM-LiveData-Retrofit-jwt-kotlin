package com.example.moviesapplication.utils

import android.app.Application
import android.content.Context

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
        fun getAppContext(): Context {
            return context
        }
    }
}