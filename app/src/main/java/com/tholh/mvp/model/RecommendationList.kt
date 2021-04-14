package com.tholh.mvp.model

import com.google.gson.annotations.SerializedName

data class RecommendationList(
    val page: Int,
    @SerializedName("results")
    val recommendations: List<Recommendation>,
    val total_pages: Int,
    val total_results: Int
)