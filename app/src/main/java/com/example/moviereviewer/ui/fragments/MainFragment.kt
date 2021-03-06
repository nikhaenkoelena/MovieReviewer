package com.example.moviereviewer.ui.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviereviewer.R
import com.example.moviereviewer.ui.adapters.MovieAdapter
import com.example.moviereviewer.ui.adapters.MovieAdapter.OnReachEndListener
import com.example.moviereviewer.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*


class MainFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private var page = 0
    private var isLoading = false
    private var lang = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle(R.string.app_name)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    companion object {
        const val POSTER_WIDTH = 185
        const val COLUMN_COUNT = 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_mf)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.detailFragment,
            R.id.mainFragment))
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment,appBarConfiguration)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        val recyclerView = recyclerViewMovies
        recyclerView.layoutManager = GridLayoutManager(context, getColumnCount())
        val adapter = MovieAdapter(context)
        recyclerView.adapter = adapter
        setOnClickListener(adapter)
        setOnReachEndListener(adapter)
        lang = Locale.getDefault().language
        page = 1
        isLoading = false
        viewModel.loadMovies(lang, page)
        getMovies(adapter)
    }

    private fun getColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / POSTER_WIDTH > COLUMN_COUNT) width / POSTER_WIDTH else COLUMN_COUNT
    }

    private fun getMovies(adapter: MovieAdapter) {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.movies = it
            isLoading = false;
            progressBar.visibility = View.INVISIBLE;
            page++;
        })
    }

    private fun setOnClickListener(adapter: MovieAdapter) {
        adapter.onPosterClickListener = object : MovieAdapter.OnPosterClickListener {
            override fun onPosterClick(position: Int) {
                val movie = adapter.movies[position]
                val bundle = Bundle()
                bundle.putInt("id", movie.id)
                navController.navigate(R.id.action_mainFragment_to_detailFragment, bundle)
            }
        }

    }

    private fun setOnReachEndListener(adapter: MovieAdapter) {
        adapter.onReachEndListener = object : OnReachEndListener {
            override fun onReachEnd() {
                if (!isLoading) {
                    viewModel.loadMovies(lang, page)
                    progressBar.visibility = View.VISIBLE
                    isLoading = true
                }
            }

        }
    }
}