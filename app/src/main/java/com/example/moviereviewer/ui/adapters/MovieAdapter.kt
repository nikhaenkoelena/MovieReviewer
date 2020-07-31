package com.example.moviereviewer.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviereviewer.R
import com.example.moviereviewer.repository.pojo.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*


class MovieAdapter(private val context: Context?): RecyclerView.Adapter<MovieAdapter.MovieViewHolder> () {

    var onPosterClickListener: OnPosterClickListener? = null
    var onReachEndListener: OnReachEndListener? =null
    var movies: List<Movie> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }

    interface OnPosterClickListener {
        fun onPosterClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (movies.size >= 20 && position > movies.size - 4 && onReachEndListener != null) {
            let {
                it.onReachEndListener?.onReachEnd()
            }
        }
        val movie = movies[position]
        if (movie.poster_path_small.isNotEmpty()) {
            Picasso.get().load(movie.poster_path_small).into(holder.poster)
        } else {
            Picasso.get().load(R.drawable.placeholder).into(holder.poster)
        }
        holder.itemView.setOnClickListener{
            onPosterClickListener?.onPosterClick(position)
        }

    }

    inner class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val poster = itemView.imageViewPoster
    }


}