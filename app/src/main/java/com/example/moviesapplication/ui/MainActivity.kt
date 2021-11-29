package com.example.moviesapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapplication.R
import com.example.moviesapplication.adapters.MoviesAdapter
import com.example.moviesapplication.databinding.ActivityMainBinding
import com.example.moviesapplication.viewmodels.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var binding: ActivityMainBinding
    private var searchText: String = ""
    private var page: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        binding.viewModel = moviesViewModel
        binding.lifecycleOwner = this
        initializeRecyclerView()
        initializeObservers()
        listener()
    }

    private fun initializeRecyclerView() {
        moviesAdapter = MoviesAdapter()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = moviesAdapter
        }
    }

    private fun initializeObservers() {
        moviesViewModel.getMovies(false).observe(this, Observer { movie ->
            moviesAdapter.setData(movie?.data!!, this@MainActivity)
        })
        moviesViewModel.mShowApiError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(it).show()
        })
        moviesViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                binding.progressBar.visibility = View.VISIBLE
                binding.floatingActionButton.hide()
            } else {
                binding.progressBar.visibility = View.GONE
                binding.floatingActionButton.show()
            }
        })
        moviesViewModel.mShowNetworkError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })
    }


    private fun listener() {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null && p0.isNotEmpty() && p0.length > 2) {
                    searchText = p0.toString()
                    moviesViewModel.searchMovies(true, searchText, page)
                        .observe(this@MainActivity, Observer { movie ->
                            moviesAdapter.setData(movie?.data!!, this@MainActivity)
                        })
                    moviesViewModel.mShowApiError.observe(this@MainActivity, Observer {
                        AlertDialog.Builder(this@MainActivity).setMessage(it).show()
                    })
                    moviesViewModel.mShowProgressBar.observe(this@MainActivity, Observer { bt ->
                        if (bt) {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.floatingActionButton.hide()
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.floatingActionButton.show()
                        }
                    })
                    moviesViewModel.mShowNetworkError.observe(this@MainActivity, Observer {
                        AlertDialog.Builder(this@MainActivity)
                            .setMessage(R.string.app_no_internet_msg).show()
                    })
                }else if (p0!!.isEmpty()){
                    moviesViewModel.getMovies(true)
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

}