package com.example.moviesapplication.repositories

import androidx.lifecycle.MutableLiveData
import com.example.moviesapplication.interfaces.NetworkResponseCallback
import com.example.moviesapplication.models.login.LoginUserModel
import com.example.moviesapplication.models.user.UserModel
import com.example.moviesapplication.models.register.RegisterUserInput
import com.example.moviesapplication.models.register.RegisterUserModel
import com.example.moviesapplication.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var registerUser: MutableLiveData<RegisterUserModel?> =
        MutableLiveData<RegisterUserModel?>()
    private var loginUser: MutableLiveData<LoginUserModel?> =
        MutableLiveData<LoginUserModel?>()
    private var user: MutableLiveData<UserModel?> =
        MutableLiveData<UserModel?>()
    private lateinit var registerUserCall: Call<RegisterUserModel>
    private lateinit var loginUserCall: Call<LoginUserModel>
    private lateinit var userCall: Call<UserModel>


    fun registerUser(
        callback: NetworkResponseCallback,
        userInput: RegisterUserInput
    ): MutableLiveData<RegisterUserModel?> {
        mCallback = callback
        if (registerUser.value != null) {
            mCallback.onResponseSuccess()
            return registerUser
        }
        registerUserCall = RestClient.getInstance().getApiService().registerUser(userInput)
        registerUserCall.enqueue(object : Callback<RegisterUserModel> {
            override fun onResponse(
                call: Call<RegisterUserModel>,
                response: Response<RegisterUserModel>
            ) {
                registerUser.value = response.body()
                mCallback.onResponseSuccess()
            }

            override fun onFailure(call: Call<RegisterUserModel>, t: Throwable) {
                registerUser.value = null
                mCallback.onResponseFailure(t)
            }

        })
        return registerUser
    }

    fun loginUser(
        callback: NetworkResponseCallback,
        username: String,
        password: String
    ): MutableLiveData<LoginUserModel?> {
        mCallback = callback
        if (loginUser.value != null) {
            mCallback.onResponseSuccess()
            return loginUser
        }
        loginUserCall = RestClient.getInstance().getApiService()
            .loginUser(grantType = "password", username, password)
        loginUserCall.enqueue(object : Callback<LoginUserModel> {
            override fun onResponse(
                call: Call<LoginUserModel>,
                response: Response<LoginUserModel>
            ) {
                loginUser.value = response.body()
                mCallback.onResponseSuccess()
            }

            override fun onFailure(call: Call<LoginUserModel>, t: Throwable) {
                loginUser.value = null
                mCallback.onResponseFailure(t)
            }

        })
        return loginUser
    }


    fun getUser(
        callback: NetworkResponseCallback,
        forceFetch: Boolean
    ): MutableLiveData<UserModel?> {
        mCallback = callback
        if (user.value != null && !forceFetch) {
            mCallback.onResponseSuccess()
            return user
        }
        var restClient=RestClient()
        userCall = restClient.getApiService().getUser()
        userCall.enqueue(object : Callback<UserModel?> {
            override fun onResponse(call: Call<UserModel?>, response: Response<UserModel?>) {
                user.value = response.body()
                mCallback.onResponseSuccess()
            }

            override fun onFailure(call: Call<UserModel?>, t: Throwable) {
                user.value = null
                mCallback.onResponseFailure(t)
            }

        })
        return user
    }

    companion object {
        private var mInstance: UserRepository? = null
        fun getInstance(): UserRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = UserRepository()
                }
            }
            return mInstance!!
        }
    }

}