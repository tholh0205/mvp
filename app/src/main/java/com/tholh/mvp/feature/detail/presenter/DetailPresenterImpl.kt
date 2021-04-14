package com.tholh.mvp.feature.detail.presenter

import com.tholh.mvp.BuildVars
import com.tholh.mvp.feature.base.presenter.BasePresenterImpl
import com.tholh.mvp.feature.detail.Type
import com.tholh.mvp.feature.detail.model.DetailItem
import com.tholh.mvp.feature.detail.view.DetailView
import com.tholh.mvp.model.*
import com.tholh.mvp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class DetailPresenterImpl(view: DetailView?, private val movieRepository: MovieRepository) :
    BasePresenterImpl<DetailView>(view), DetailPresenter {

    private var movieId: Int = 0
    private var detailItemList = ArrayList<DetailItem>()

    override fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    override fun loadData() {
        view?.showLoading()
        launch {
            val movieResponse = getMovieDetail()
            val creditResponse = getCredit()
            val videoResponse = getVideos()
            val reviewResponse = getReviews()
            val recommendationResponse = getRecommendations()
            withContext(Dispatchers.Main) {
                if (movieResponse.isSuccessful) {
                    val movieDetail = ArrayList<MovieDetail>()
                    movieDetail.add(movieResponse.body()!!)
                    val detailItem = DetailItem(Type.TYPE_MOVIE_DETAIL, movieDetail)
                    detailItemList.add(detailItem)
                    val yourRateItem = DetailItem(Type.TYPE_YOUR_RATE, null)
                    detailItemList.add(yourRateItem)
                }
                if (creditResponse.isSuccessful) {
                    val detailItem = DetailItem(Type.TYPE_CAST, creditResponse.body()!!.cast)
                    detailItemList.add(detailItem)
                }
                if (videoResponse.isSuccessful) {
                    val detailItem = DetailItem(Type.TYPE_VIDEO, videoResponse.body()!!.videos)
                    detailItemList.add(detailItem)
                }
                if (reviewResponse.isSuccessful) {
                    val detailItem = DetailItem(Type.TYPE_REVIEW, reviewResponse.body()!!.reviews)
                    detailItemList.add(detailItem)
                }
                if (recommendationResponse.isSuccessful) {
                    val detailItem = DetailItem(Type.TYPE_RECOMMENDATION, recommendationResponse.body()!!.recommendations)
                    detailItemList.add(detailItem)
                }
                view?.bindData(detailItemList)
                view?.dismissLoading()
            }
        }
    }

    override fun loadVideos() {
        launch {
            val response = getVideos()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    view?.bindVideos(response.body()!!.videos)
                }
            }
        }
    }

    private suspend fun getVideos(): Response<VideoList> {
        return movieRepository.getVideos(apiKey = BuildVars.API_KEY, movieId = movieId)
    }

    override fun loadRecommendations() {
        launch {
            val response = getRecommendations()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    view?.bindRecommendation(response.body()!!.recommendations)
                }
            }
        }
    }

    private suspend fun getRecommendations(): Response<RecommendationList> {
        return movieRepository.getRecommendations(
            apiKey = BuildVars.API_KEY,
            movieId = movieId,
            page = 1
        )
    }

    override fun loadCredit() {
        launch {
            val response = getCredit()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    view?.bindCast(response.body()!!.cast)
                }
            }
        }
    }

    private suspend fun getCredit(): Response<Credit> {
        return movieRepository.getCredits(apiKey = BuildVars.API_KEY, movieId = movieId)
    }

    override fun loadReviews() {
        launch {
            val response = getReviews()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    view?.bindReviews(response.body()!!.reviews)
                }
            }
        }
    }

    private suspend fun getReviews(): Response<ReviewList> {
        return movieRepository.getReviews(apiKey = BuildVars.API_KEY, movieId = movieId, page = 1)
    }

    override fun loadMovieDetail() {

    }

    private suspend fun getMovieDetail(): Response<MovieDetail> {
        return movieRepository.getDetails(apiKey = BuildVars.API_KEY, movieId = movieId)
    }

}