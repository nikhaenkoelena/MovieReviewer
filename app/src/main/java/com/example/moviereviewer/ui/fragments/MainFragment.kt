package com.example.moviereviewer.ui.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviereviewer.R
import com.example.moviereviewer.ui.adapters.MovieAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle(R.string.app_title)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    companion object {
        const val POSTER_WIDTH = 185
        const val COLUMN_COUNT = 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val recyclerView = recyclerViewMovies
        recyclerView.layoutManager = GridLayoutManager(context, getColumnCount())
        val adapter = MovieAdapter()
        recyclerView.adapter = adapter
    }

    fun getColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / POSTER_WIDTH > COLUMN_COUNT) width / POSTER_WIDTH else COLUMN_COUNT
    }
}