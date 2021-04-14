package com.tholh.mvp.feature.home.presenter

import com.tholh.mvp.BuildVars
import com.tholh.mvp.feature.base.presenter.BasePresenterImpl
import com.tholh.mvp.feature.home.Type
import com.tholh.mvp.feature.home.model.HomeItem
import com.tholh.mvp.feature.home.view.HomeView
import com.tholh.mvp.model.Genre
import com.tholh.mvp.model.GenreList
import com.tholh.mvp.model.Movie
import com.tholh.mvp.model.MovieList
import com.tholh.mvp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
class HomePresenterImpl(
    view: HomeView?,
    private val movieRepository: MovieRepository
) : BasePresenterImpl<HomeView>(view), HomePresenter {

    private var pageTrending = 1
    private var canLoadMoreTrending = true
    private var isGettingTrending = false
    private var pagePopular = 1
    private var canLoadMorePopular = true
    private var isGettingPopular = false
    private var pageTopRated = 1
    private var canLoadMoreTopRate = true
    private var isGettingTopRated = false
    private var pageUpcoming = 1
    private var canLoadMoreUpcoming = true
    private var isGettingUpcoming = false
    private var homeItemList = ArrayList<HomeItem>()

    override fun loadData() {
        if (homeItemList.isEmpty()) {
            view?.showLoading()
        }
        launch {
            pageTrending = 1
            canLoadMoreTrending = true
            isGettingTrending = false
            pagePopular = 1
            canLoadMorePopular = true
            isGettingPopular = false
            pageTopRated = 1
            canLoadMoreTopRate = true
            isGettingTopRated = false
            pageUpcoming = 1
            canLoadMoreUpcoming = true
            isGettingUpcoming = false
            val trendingResponse = getTrending()
            val popularResponse = getPopular()
            val topRatedResponse = getTopRated()
            val upcomingResponse = getUpcoming()
            val movieGenreResponse = getMovieGenres()
            val tvGenreResponse = getTvGenres()
            withContext(Dispatchers.Main) {
                homeItemList.clear()
                if (trendingResponse.isSuccessful) {
                    val homeItem = HomeItem(Type.TYPE_TRENDING, trendingResponse.body()!!.movies)
                    homeItemList.add(homeItem)
                    canLoadMoreTrending = pageTrending < trendingResponse.body()!!.total_pages
                    pageTrending++
                }
                val genres = ArrayList<Genre>()
                if (movieGenreResponse.isSuccessful) {
                    genres.addAll(movieGenreResponse.body()!!.genres)
                }
                if (tvGenreResponse.isSuccessful) {
                    genres.addAll(tvGenreResponse.body()!!.genres)
                }
                if (!genres.isNullOrEmpty()) {
                    val homeItem = HomeItem(Type.TYPE_GENRE, genres)
                    homeItemList.add(homeItem)
                }
                if (popularResponse.isSuccessful) {
                    val homeItem = HomeItem(Type.TYPE_POPULAR, popularResponse.body()!!.movies)
                    homeItemList.add(homeItem)
                    canLoadMorePopular = pagePopular < popularResponse.body()!!.total_pages
                    pagePopular++
                }
                if (topRatedResponse.isSuccessful) {
                    val homeItem = HomeItem(Type.TYPE_TOP_RATED, topRatedResponse.body()!!.movies)
                    homeItemList.add(homeItem)
                    canLoadMoreTopRate = pageTopRated < topRatedResponse.body()!!.total_pages
                    pageTopRated++
                }
                if (upcomingResponse.isSuccessful) {
                    val homeItem = HomeItem(Type.TYPE_UPCOMING, upcomingResponse.body()!!.movies)
                    homeItemList.add(homeItem)
                    canLoadMoreUpcoming = pageUpcoming < upcomingResponse.body()!!.total_pages
                    pageUpcoming++
                }
                view?.bindHomeData(homeItemList)
                view?.showRefreshing(false)
                view?.dismissLoading()
            }
        }
    }

    private suspend fun getMovieGenres(): Response<GenreList> {
        return movieRepository.getMovieGenres(apiKey = BuildVars.API_KEY)
    }

    private suspend fun getTvGenres(): Response<GenreList> {
        return movieRepository.getTvGenres(apiKey = BuildVars.API_KEY)
    }

    override fun loadTrending() {
        if (isGettingTrending || !canLoadMoreTrending) {
            return
        }
        isGettingTrending = true
        view?.showLoadMoreTrending(true)
        launch {
            val response = getTrending()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    canLoadMoreTrending = pageTrending < response.body()!!.total_pages
                    pageTrending++
                    val trendingItem = homeItemList.find { it.id == Type.TYPE_TRENDING }
                    (trendingItem?.children as ArrayList?)?.addAll(response.body()!!.movies)
                    view?.showLoadMoreTrending(false)
                    view?.bindTrendingData(trending = trendingItem?.children as List<Movie>?)
                } else {
                    view?.showLoadMoreTrending(false)
                    view?.showGenericError()
                }
                isGettingTrending = false
            }
        }
    }

    private suspend fun getTrending(): Response<MovieList> {
        return movieRepository.getTrending(apiKey = BuildVars.API_KEY, page = pageTrending)
    }

    override fun loadPopular() {
        if (isGettingPopular || !canLoadMorePopular) {
            return
        }
        isGettingPopular = true
        view?.showLoadMorePopular(true)
        launch {
            val response = getPopular()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    canLoadMorePopular = pagePopular < response.body()!!.total_pages
                    pagePopular++
                    val popularItem = homeItemList.find { it.id == Type.TYPE_POPULAR }
                    (popularItem?.children as ArrayList?)?.addAll(response.body()!!.movies)
                    view?.showLoadMorePopular(false)
                    view?.bindPopularData(popularItem?.children as List<Movie>?)
                } else {
                    view?.showLoadMorePopular(false)
                    view?.showGenericError()
                }
                isGettingPopular = false
            }
        }
    }

    private suspend fun getPopular(): Response<MovieList> {
        return movieRepository.getPopular(apiKey = BuildVars.API_KEY, page = pagePopular)
    }

    override fun loadTopRated() {
        if (isGettingTopRated || !canLoadMoreTopRate) {
            return
        }
        isGettingTopRated = true
        view?.showLoadMoreTopRated(true)
        launch {
            val response = getTopRated()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    canLoadMoreTopRate = pageTopRated < response.body()!!.total_pages
                    pageTopRated++
                    val topRatedItem = homeItemList.find { it.id == Type.TYPE_TOP_RATED }
                    (topRatedItem?.children as ArrayList?)?.addAll(response.body()!!.movies)
                    view?.showLoadMoreTopRated(false)
                    view?.bindTopRatedData(topRatedItem?.children as List<Movie>?)
                } else {
                    view?.showLoadMoreTopRated(false)
                    view?.showGenericError()
                }
                isGettingTopRated = false
            }
        }
    }

    private suspend fun getTopRated(): Response<MovieList> {
        return movieRepository.getTopRated(apiKey = BuildVars.API_KEY, page = pageTopRated)
    }

    override fun loadUpcoming() {
        if (isGettingUpcoming || !canLoadMoreUpcoming) {
            return
        }
        isGettingUpcoming = true
        view?.showLoadMoreUpcoming(true)
        launch {
            val response = getUpcoming()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    canLoadMoreUpcoming = pageUpcoming < response.body()!!.total_pages
                    pageUpcoming++
                    val upcomingItem = homeItemList.find { it.id == Type.TYPE_UPCOMING }
                    (upcomingItem?.children as ArrayList?)?.addAll(response.body()!!.movies)
                    view?.showLoadMoreUpcoming(false)
                    view?.bindUpcomingData(upcomingItem?.children as List<Movie>?)
                } else {
                    view?.showLoadMoreUpcoming(false)
                    view?.showGenericError()
                }
                isGettingUpcoming = false
            }
        }
    }

    private suspend fun getUpcoming(): Response<MovieList> {
        return movieRepository.getUpcoming(apiKey = BuildVars.API_KEY, page = pageTopRated)
    }


}