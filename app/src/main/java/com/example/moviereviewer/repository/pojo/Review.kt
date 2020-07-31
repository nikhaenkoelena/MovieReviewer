package com.example.moviereviewer.repository.pojo

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reviews_table")
data class Review(
    @SerializedName("author")
    @Expose
    var author: String = "",
    @SerializedName("content")
    @Expose
    var content: String = "",
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    var id: String = "",
    @SerializedName("url")
    @Expose
    var url: String = ""
)