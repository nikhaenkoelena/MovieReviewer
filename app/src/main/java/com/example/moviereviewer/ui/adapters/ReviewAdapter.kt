package com.example.moviereviewer.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviereviewer.R
import com.example.moviereviewer.repository.pojo.Review
import kotlinx.android.synthetic.main.review_item.view.*

class ReviewAdapter(private val context: Context?) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    var reviews: List<Review> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        with(holder) {
            textViewReviewAuthor.text = review.author
            textViewReviewText.text = review.content
        }
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewReviewAuthor = itemView.textViewReviewAuthor as TextView
        val textViewReviewText = itemView.textViewReviewText as TextView
    }

}