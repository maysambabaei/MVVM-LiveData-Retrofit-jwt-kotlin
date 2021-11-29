package com.example.moviesapplication.ui.moviesdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.ActivityMoviesDetailBinding
import com.example.moviesapplication.viewmodels.MoviesDetailViewModel
import android.R.string


class MoviesDetailActivity : AppCompatActivity() {
    private lateinit var moviesDetailViewModel: MoviesDetailViewModel
    private lateinit var binding: ActivityMoviesDetailBinding
    private var movieId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_detail)
        movieId = intent.getIntExtra("movieId", 0)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_detail)
        moviesDetailViewModel = ViewModelProvider(this).get(MoviesDetailViewModel::class.java)
        binding.viewModel = moviesDetailViewModel
        binding.lifecycleOwner = this
        initializeObservers()
    }

    private fun initializeObservers() {
        binding.progressBar.visibility = View.VISIBLE
        moviesDetailViewModel.getMovie(true, movieId).observe(this, Observer { movie ->
            Glide.with(this@MoviesDetailActivity)
                .load(movie?.images?.get(0))
                .into(binding.ivPoster)
            binding.tvTitle.text = movie?.title
            binding.tvYear.text = movie?.year
            binding.tvRated.text = movie?.rated
            binding.tvReleased.text = movie?.released
            binding.tvRunTime.text = movie?.runtime
            binding.tvDirector.text = movie?.director
            binding.tvWriter.text = movie?.writer
            binding.tvActor.text = movie?.actors
            binding.tvPlot.text = movie?.plot
            binding.tvCountry.text = movie?.country
            binding.tvAwards.text = movie?.awards
            binding.tvMetaScore.text = movie?.metascore
            binding.tvImdbRating.text = movie?.imdbRating
            binding.tvImdbVotes.text = movie?.imdbVotes
            binding.tvImdbId.text = movie?.imdbId
            binding.tvType.text = movie?.type
            binding.tvGenres.text = movie?.genres.toString()
            Glide.with(this@MoviesDetailActivity)
                .load(movie?.images?.get(0))
                .into(binding.ivOne)
            Glide.with(this@MoviesDetailActivity)
                .load(movie?.images?.get(1))
                .into(binding.ivTwo)
            Glide.with(this@MoviesDetailActivity)
                .load(movie?.images?.get(2))
                .into(binding.ivThree)

        })
        moviesDetailViewModel.mShowApiError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(it).show()
        })
        moviesDetailViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        moviesDetailViewModel.mShowNetworkError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}