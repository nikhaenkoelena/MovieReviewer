package com.example.moviereviewer.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviereviewer.R
import com.example.moviereviewer.repository.pojo.Trailer
import kotlinx.android.synthetic.main.trailer_item.view.*

class TrailersAdapter(private val context: Context?) :
    RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder>() {

    var onTrailerClickListener: OnTrailerClickListener? = null

    var trailers: List<Trailer> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnTrailerClickListener {
        fun onTrailerClick(url: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trailer_item, parent, false)
        return TrailerViewHolder(view)
    }

    override fun getItemCount() = trailers.size

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = trailers[position]
        holder.textViewTrailersTitle.text = trailer.name
        holder.itemView.setOnClickListener {
            onTrailerClickListener?.onTrailerClick(trailers[position].key)
        }
    }

    inner class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTrailersTitle = itemView.textViewTrailersTitle as TextView
    }
}