package com.example.moviesapplication.interfaces

interface NetworkResponseCallback {

    fun onResponseSuccess()

    fun onResponseFailure(th: Throwable)
}