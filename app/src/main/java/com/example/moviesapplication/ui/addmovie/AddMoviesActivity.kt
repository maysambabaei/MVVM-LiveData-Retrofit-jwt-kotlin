package com.example.moviesapplication.ui.addmovie

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.ActivityAddMoviesBinding
import com.example.moviesapplication.models.addmovies.MovieInput
import com.example.moviesapplication.viewmodels.AddMoviesViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class AddMoviesActivity : AppCompatActivity() {

    private lateinit var addMoviesViewModel: AddMoviesViewModel
    private lateinit var binding: ActivityAddMoviesBinding
    private var PICK_IMAGE_MULTIPLE = 100
    private var context: Context? = null
    private lateinit var file: File
    var title = ""
    var imdbId = ""
    var country = ""
    var year = ""
    var director = ""
    var imdbRating = ""
    var imdbVotes = ""
    private lateinit var poster: File
    private var movieInput: MovieInput = MovieInput()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movies)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_movies)
        addMoviesViewModel = ViewModelProvider(this).get(AddMoviesViewModel::class.java)
        binding.viewModel = addMoviesViewModel
        binding.lifecycleOwner = this
        listener()
        context = this@AddMoviesActivity
    }

    private fun listener() {
        binding.ivPoster.setOnClickListener {
            getImage()
        }
        binding.btSave.setOnClickListener {
            title = binding.etName.text.toString()
            imdbId = binding.etImdbId.text.toString()
            country = binding.etCountry.text.toString()
            year = binding.etYear.text.toString()
            director = binding.etDirector.text.toString()
            imdbRating = binding.etImdbRating.text.toString()
            imdbVotes = binding.etImdbVotes.text.toString()
            if (title.isEmpty() || imdbId.isEmpty() || country.isEmpty() || year.isEmpty()) {
                Toast.makeText(
                    context,
                    "فیلد های نام فیلم , شناسه فیلم در imdb ,کشور سازنده و سال ساخت اجباری میباشند",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                movieInput =
                    MovieInput(
                        title,
                        imdbId,
                        country,
                        year.toInt(),
                        director,
                        imdbRating,
                        imdbVotes,
                        poster
                    )
                addMoviesViewModel.addMovie(true, movieInput).observe(this, Observer { movie ->
                    Toast.makeText(
                        this@AddMoviesActivity,
                        "${movie?.id.toString()}\n ${movie?.title}\n ${movie?.year.toString()}\n${movie?.country}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                })
                addMoviesViewModel.mShowApiError.observe(this, Observer {
                    AlertDialog.Builder(this).setMessage(it).show()
                })
                addMoviesViewModel.mShowProgressBar.observe(this, Observer { bt ->
                    if (bt) {
                        binding.progressBar.visibility = View.VISIBLE

                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                })
                addMoviesViewModel.mShowNetworkError.observe(this, Observer {
                    AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
                })
            }
        }
    }

    private fun getImage() {
        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE
            )
        } else {
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            getResult.launch(intent)
        }
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data?.data != null) {
                    val selectedImage: Uri = it.data?.data!!
                    var bitmap: Bitmap? = null
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    file = File(context!!.cacheDir, "Poster Image")
                    file.createNewFile()
                    val bos = ByteArrayOutputStream()
                    bitmap?.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
                    val bitmapdata: ByteArray = bos.toByteArray()
                    val fos = FileOutputStream(file)
                    fos.write(bitmapdata)
                    fos.flush()
                    fos.close()
                    poster = file
                    binding.ivPoster.setImageBitmap(bitmap)
                }
            }
        }
}