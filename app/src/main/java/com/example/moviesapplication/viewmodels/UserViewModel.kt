package com.example.moviesapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.interfaces.NetworkResponseCallback
import com.example.moviesapplication.models.login.LoginUserModel
import com.example.moviesapplication.models.register.RegisterUserInput
import com.example.moviesapplication.models.register.RegisterUserModel
import com.example.moviesapplication.models.user.UserModel
import com.example.moviesapplication.repositories.UserRepository
import com.example.moviesapplication.utils.NetworkHelper

class UserViewModel(private val app: Application) : AndroidViewModel(app) {

    private lateinit var registerUser: MutableLiveData<RegisterUserModel?>
    private lateinit var profileUser: MutableLiveData<UserModel?>
    private lateinit var user: MutableLiveData<LoginUserModel?>
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    var mRepository = UserRepository.getInstance()

    fun registerUser(userInput: RegisterUserInput): MutableLiveData<RegisterUserModel?> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            registerUser = mRepository.registerUser(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            }, userInput)
        } else {
            mShowNetworkError.value = true
        }
        return registerUser
    }

    fun loginUser(username: String, password: String): MutableLiveData<LoginUserModel?> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            user = mRepository.loginUser(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

             }, username, password)
        } else {
            mShowNetworkError.value = true
        }
        return user
    }

    fun getUser(): MutableLiveData<UserModel?> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            profileUser = mRepository.getUser(object : NetworkResponseCallback {
                override fun onResponseSuccess() {
                    mShowProgressBar.value = false
                }

                override fun onResponseFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

            })
        } else {
            mShowNetworkError.value = true
        }
        return profileUser
    }



}