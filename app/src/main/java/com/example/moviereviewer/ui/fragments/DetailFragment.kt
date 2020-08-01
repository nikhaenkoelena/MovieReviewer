package com.example.moviereviewer.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private lateinit var detailViewModel: DetailViewModel
    private var movie: Movie? = null
    private var lang = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getParcelable("movie")
        Picasso.get().load(movie?.poster_path_big).placeholder(R.drawable.placeholder)
            .into(imageViewBigPoster)
        movie?.let {
            with(it) {
                activity?.title = title
                textViewTitle.text = title
                textViewOriginTitle.text = original_title
                textViewReleaseDate.text = release_date
                textViewOverView.text = overview
            }
        }
        val recyclerView = recyclerViewReviews
        val recyclerViewT = recyclerViewTrailers
        val reviewAdapter = ReviewAdapter(context)
        val trailersAdapter = TrailersAdapter(context)
        recyclerView.adapter = reviewAdapter
        recyclerViewT.adapter = trailersAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerViewT.layoutManager = LinearLayoutManager(context)
        setOnClickListenerAddToFavorite()
        setOnTrailerClickListener(trailersAdapter)
        detailViewModel =
            ViewModelProviders.of(this).get<DetailViewModel>(DetailViewModel::class.java)
        lang = Locale.getDefault().language
        val id = movie?.id
        if (id != null)
            with(detailViewModel) {
                loadReviews(id, lang, context)
                loadTrailers(id, lang, context)
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

        }
    }
}