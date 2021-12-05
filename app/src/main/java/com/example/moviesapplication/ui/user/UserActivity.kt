package com.example.moviesapplication.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.ActivityLoginBinding
import com.example.moviesapplication.databinding.ActivityUserBinding
import com.example.moviesapplication.viewmodels.MoviesViewModel
import com.example.moviesapplication.viewmodels.UserViewModel

class UserActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.viewModel = userViewModel
        binding.lifecycleOwner = this
        initializeObservers()
        listener()
    }

    private fun listener() {
        binding.btClose.setOnClickListener {
            finish()
        }
    }

    private fun initializeObservers() {
        userViewModel.getUser(true).observe(this, Observer { user ->
            binding.progressBar.visibility = View.VISIBLE
            binding.tvName.text = user?.name
            binding.tvCreatedAt.text = user?.createdAt
            binding.tvEmail.text = user?.email
            binding.tvUpdatedAt.text = user?.updatedAt
        })
        userViewModel.mShowApiError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(it).show()
            binding.progressBar.visibility = View.GONE
        })
        userViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                binding.progressBar.visibility = View.VISIBLE

            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        userViewModel.mShowNetworkError.observe(this, Observer {
            binding.progressBar.visibility = View.GONE
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })
    }

}