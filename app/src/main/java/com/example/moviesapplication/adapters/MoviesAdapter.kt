package com.example.moviesapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapplication.R
import com.example.moviesapplication.models.movieslist.MoviesData
import com.example.moviesapplication.ui.moviesdetail.MoviesDetailActivity
import java.text.FieldPosition

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    var movies: List<MoviesData>? = listOf()
    var context: Context? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MoviesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movies, p0,false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        var movie = movies?.get(position)
        context?.let {
            Glide.with(it)
                .load(movie?.poster.toString())
                .into(holder.avatar)
        }
        holder.title.text = movie?.title.toString()
        holder.year.text = movie?.year.toString()
        holder.country.text = movie?.country.toString()
        holder.rating.text = movie?.imdbrating.toString()
        holder.genres.text = movie?.genres.toString()
        holder.cvMovies.setOnClickListener {
            // go to detail page
            var intent=Intent(context,MoviesDetailActivity::class.java)
            intent.putExtra("movieId",movie?.id)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if (movies != null) {
            movies?.size!!
        } else {
            0
        }

    }

    fun setData(moviesList: List<MoviesData>, context: Context) {
        this.movies = moviesList
        this.context = context
        notifyDataSetChanged()
    }

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var avatar: AppCompatImageView = itemView.findViewById(R.id.iv_poster)
        var title: AppCompatTextView = itemView.findViewById(R.id.tv_title)
        var year: AppCompatTextView = itemView.findViewById(R.id.tv_year)
        var country: AppCompatTextView = itemView.findViewById(R.id.tv_country)
        var rating: AppCompatTextView = itemView.findViewById(R.id.tv_rating)
        var genres: AppCompatTextView = itemView.findViewById(R.id.tv_genres)
        var cvMovies: CardView = itemView.findViewById(R.id.cv_movies)

    }
}