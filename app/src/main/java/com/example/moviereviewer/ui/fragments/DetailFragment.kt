package com.example.moviereviewer.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviereviewer.R
import com.example.moviereviewer.repository.pojo.Movie
import com.example.moviereviewer.ui.adapters.ReviewAdapter
import com.example.moviereviewer.ui.adapters.TrailersAdapter
import com.example.moviereviewer.viewmodels.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.movie_info.*
import java.util.*


class DetailFragment : Fragment() {

    companion object{
        const val IS_FAVORITE = true
        const val  IS_NOT_FAVORITE = false
    }

    private lateinit var detailViewModel: DetailViewModel
    private var movieId: Int = 0
    private lateinit var movie: Movie
    private var lang = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        if (id != null) {
           movieId = id
       }
        detailViewModel =
            ViewModelProviders.of(this).get<DetailViewModel>(DetailViewModel::class.java)
        detailViewModel.loadMovieById(movieId)
        detailViewModel.movieById.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            movie = it
            Picasso.get().load(it.poster_path_big).placeholder(R.drawable.placeholder)
                .into(imageViewBigPoster)
            loadAddToFavoriteButton(it.isFavorite)
            with(it) {
                activity?.title = title
                textViewTitle.text = title
                textViewOriginTitle.text = original_title
                textViewReleaseDate.text = release_date
                textViewOverView.text = overview
            }
        })
        val recyclerView = recyclerViewReviews
        val recyclerViewT = recyclerViewTrailers
        val reviewAdapter = ReviewAdapter(context)
        val trailersAdapter = TrailersAdapter(context)
        recyclerView.adapter = reviewAdapter
        recyclerViewT.adapter = trailersAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerViewT.layoutManager = LinearLayoutManager(context)
        setOnTrailerClickListener(trailersAdapter)
            setOnClickListenerAddToFavorite()
        lang = Locale.getDefault().language
            with(detailViewModel) {
                loadReviews(movieId, lang, context)
                loadTrailers(movieId, lang, context)
            }
        getReviews(reviewAdapter)
        getTrailers(trailersAdapter)
    }

    private fun getReviews(adapter: ReviewAdapter) {
        detailViewModel.reviews.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.reviews = it
        })
    }

    private fun getTrailers(adapter: TrailersAdapter) {
        detailViewModel.trailers.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.trailers = it
        })
    }

    private fun setOnTrailerClickListener(adapter: TrailersAdapter) {
        adapter.onTrailerClickListener = object : TrailersAdapter.OnTrailerClickListener {
            override fun onTrailerClick(url: String) {
                val intentToTrailer = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intentToTrailer)
            }
        }
    }

    private fun setOnClickListenerAddToFavorite() {
        imageViewAddToFavorite.setOnClickListener {
            if (movie.isFavorite) {
                setFavorite(IS_NOT_FAVORITE)
                Toast.makeText(context, "Removed from favorite", Toast.LENGTH_SHORT).show()
                loadAddToFavoriteButton(IS_NOT_FAVORITE)
            } else {
                setFavorite(IS_FAVORITE)
                Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT).show()
                loadAddToFavoriteButton(IS_FAVORITE)
            }
        }
    }

    private fun loadAddToFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            Picasso.get().load(R.drawable.delete_from_fav).into(imageViewAddToFavorite)
        } else {
            Picasso.get().load(R.drawable.add_to_fav).into(imageViewAddToFavorite)
        }
    }

    private fun setFavorite(isFavorite: Boolean) {
        detailViewModel.setFavoriteOption(movie.id, isFavorite)
    }

}