package com.example.moviesapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.R
import com.example.moviesapplication.models.genres.GenresData
import com.example.moviesapplication.viewmodels.MoviesViewModel

class GenresAdapter : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {
    var genres: List<GenresData>? = listOf()
    var context: Context? = null
    private lateinit var viewModel: MoviesViewModel
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GenresViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_genres, p0, false)
        return GenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        var ganre = genres?.get(position)
        holder.tvName.text = ganre?.name.toString()
        holder.lyGenres.setOnClickListener {
            viewModel.getMovies(true)
        }
    }

    override fun getItemCount(): Int {
        return if (genres != null) {
            genres?.size!!
        } else {
            0
        }

    }

    fun setData(genresList: List<GenresData>, context: Context, moviesViewModel: MoviesViewModel) {
        this.genres = genresList
        this.context = context
        this.viewModel = moviesViewModel
        notifyDataSetChanged()
    }

    class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvName: AppCompatTextView = itemView.findViewById(R.id.tv_name)
        var lyGenres: ConstraintLayout = itemView.findViewById(R.id.ly_genres)

    }
}