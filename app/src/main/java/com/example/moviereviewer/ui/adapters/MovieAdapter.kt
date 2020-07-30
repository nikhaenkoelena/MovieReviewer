package com.example.moviereviewer.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviereviewer.R
import com.example.moviereviewer.repository.pojo.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder> () {

    var movies = arrayListOf<Movie>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        if (movie.poster_path_small.isNotEmpty()) {
            Picasso.get().load(movie.poster_path_small).into(holder.poster)
        } else {
            //TODO loading placeholder image
        }
    }

    inner class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val poster = itemView.imageViewPoster
    }
}