package com.example.moviereviewer.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviereviewer.R
import com.example.moviereviewer.repository.pojo.Movie
import com.example.moviereviewer.ui.adapters.MovieAdapter
import com.example.moviereviewer.ui.adapters.ReviewAdapter
import com.example.moviereviewer.viewmodels.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_main.*
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
        val reviewAdapter = ReviewAdapter(context)
        recyclerView.adapter = reviewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        setOnClickListenerAddToFavorite()
        detailViewModel =
            ViewModelProviders.of(this).get<DetailViewModel>(DetailViewModel::class.java)
        lang = Locale.getDefault().language
        detailViewModel.loadReviews(id, lang, context)
        getReviews(reviewAdapter)
    }

    private fun getReviews(adapter: ReviewAdapter) {
        detailViewModel.reviews.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.reviews = it
        })
    }

    private fun setOnClickListenerAddToFavorite() {
        imageViewAddToFavorite.setOnClickListener {

        }
    }
}