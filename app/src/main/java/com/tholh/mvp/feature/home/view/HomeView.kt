package com.tholh.mvp.feature.home.view

import com.tholh.mvp.feature.base.view.BaseView
import com.tholh.mvp.feature.home.model.HomeItem
import com.tholh.mvp.model.Movie

interface HomeView: BaseView {

    fun showRefreshing(show: Boolean)
    fun bindHomeData(homeData : List<HomeItem>)
    fun showLoadMoreTrending(show: Boolean)
    fun bindTrendingData(trending: List<Movie>?)
    fun showLoadMorePopular(show: Boolean)
    fun bindPopularData(popularList: List<Movie>?)
    fun showLoadMoreTopRated(show: Boolean)
    fun bindTopRatedData(topRatedList: List<Movie>?)
    fun showLoadMoreUpcoming(show: Boolean)
    fun bindUpcomingData(upcomingList: List<Movie>?)

}