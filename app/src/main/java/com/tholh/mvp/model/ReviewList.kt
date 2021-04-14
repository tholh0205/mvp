package com.tholh.mvp.model

import com.google.gson.annotations.SerializedName

data class ReviewList(
    val id: Int,
    val page: Int,
    @SerializedName("results")
    val reviews: List<Review>,
    val total_pages: Int,
    val total_results: Int
)