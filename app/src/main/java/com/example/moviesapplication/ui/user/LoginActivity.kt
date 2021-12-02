package com.example.moviesapplication.ui.user

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Config
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.ActivityLoginBinding
import com.example.moviesapplication.models.register.RegisterUserInput
import com.example.moviesapplication.ui.MainActivity
import com.example.moviesapplication.viewmodels.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityLoginBinding
    private var email = ""
    private var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.viewModel = userViewModel
        binding.lifecycleOwner = this
        listener()
    }


    private fun listener() {
        binding.btLogin.setOnClickListener {
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "نام کاربری و رمز عبور را وارد کنید", Toast.LENGTH_LONG).show()
            } else {
                userViewModel.loginUser(email, password).observe(this, Observer { login ->
                    if (login?.accessToken!=null) {
                        val sharedPreferences: SharedPreferences =
                            this@LoginActivity.getSharedPreferences(
                                "myPrefs",
                                Context.MODE_PRIVATE
                            )
                        val editor = sharedPreferences.edit()
                        editor.putString("PREFS_AUTH_ACCESSES_TOKEN", login?.accessToken.toString())
                        editor.putString("AUTH_REFRESH_TOKEN", login?.refreshToken.toString())
                        editor.commit()
                        finish()
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "ورود با خطا مواجه شد",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
                userViewModel.mShowApiError.observe(this, Observer {
                    AlertDialog.Builder(this).setMessage(it).show()
                })
                userViewModel.mShowProgressBar.observe(this, Observer { bt ->
                    if (bt) {
                        binding.progressBar.visibility = View.VISIBLE

                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                })
                userViewModel.mShowNetworkError.observe(this, Observer {
                    AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
                })
            }
        }
        binding.tvRegister.setOnClickListener {
            var intent = Intent(this@LoginActivity, RegisterUserActivity::class.java)
            startActivity(intent)
        }
    }
}