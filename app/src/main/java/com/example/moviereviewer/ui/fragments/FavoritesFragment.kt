package com.example.moviereviewer.ui.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviereviewer.R
import com.example.moviereviewer.ui.adapters.MovieAdapter
import com.example.moviereviewer.viewmodels.DetailViewModel
import com.example.moviereviewer.viewmodels.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        recyclerViewFavouriteMovies.layoutManager = GridLayoutManager(context, getColumnCount())
        val adapter = MovieAdapter(context)
        recyclerViewFavouriteMovies.adapter = adapter
        viewModel =
            ViewModelProviders.of(this).get<FavoriteViewModel>(FavoriteViewModel::class.java)
        setOnPosterClickListener(adapter)
        getFavouriteMovie(adapter)
    }

    private fun getColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / MainFragment.POSTER_WIDTH > MainFragment.COLUMN_COUNT) width / MainFragment.POSTER_WIDTH else MainFragment.COLUMN_COUNT
    }

    private fun setOnPosterClickListener(adapter: MovieAdapter) {
        adapter.onPosterClickListener = object : MovieAdapter.OnPosterClickListener {
            override fun onPosterClick(position: Int) {
                val movie = adapter.movies[position]
                val bundle = Bundle()
                bundle.putInt("id", movie.id)
                navController.navigate(R.id.action_favoritesFragment_to_detailFragment, bundle)
            }
        }
    }

    private fun getFavouriteMovie(adapter: MovieAdapter) {
        viewModel.favoriteMovies.observe(viewLifecycleOwner, Observer {
            adapter.movies = it
        })
    }
}