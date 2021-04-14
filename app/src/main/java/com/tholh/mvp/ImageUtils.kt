package com.tholh.mvp

object ImageUtils {

    private var baseUrl = "https://image.tmdb.org/t/p/"

    fun getFullUrl(imageName: String?) : String {
        if (imageName != null && imageName.contains("http")) {
            return imageName.replace("/http", "http")
        }
        return baseUrl + "w500/" + imageName
    }

    fun getFullOriginalUrl(imageName: String?) : String {
        return baseUrl + "original/" + imageName
    }

    fun getYoutubeThumb(videoId: String) : String {
        return "https://img.youtube.com/vi/$videoId/1.jpg"
    }

}