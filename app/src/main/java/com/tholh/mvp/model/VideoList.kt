package com.tholh.mvp.model

import com.google.gson.annotations.SerializedName

data class VideoList(
    val id: Int,
    @SerializedName("results")
    val videos: List<Video>
)