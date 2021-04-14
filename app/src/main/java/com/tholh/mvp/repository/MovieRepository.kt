package com.tholh.mvp.repository

import com.tholh.mvp.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRepository {

    @GET("movie/{movie_id}")
    suspend fun getDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetail>

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<Credit>

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<ReviewList>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<RecommendationList>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<VideoList>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("region") region: String = "",
        @Query("page") page: Int
    ): Response<MovieList>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("region") region: String = "",
        @Query("page") page: Int
    ): Response<MovieList>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("region") region: String = "",
        @Query("page") page: Int
    ): Response<MovieList>

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(
        @Path("media_type") mediaType: String = "all",
        @Path("time_window") timeWindow: String = "day",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieList>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(@Query("api_key") apiKey: String) : Response<GenreList>

    @GET("genre/tv/list")
    suspend fun getTvGenres(@Query("api_key") apiKey: String) : Response<GenreList>
}