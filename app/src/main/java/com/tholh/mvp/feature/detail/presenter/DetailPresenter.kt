package com.tholh.mvp.feature.detail.presenter

import com.tholh.mvp.feature.base.presenter.BasePresenter
import com.tholh.mvp.feature.detail.view.DetailView

interface DetailPresenter : BasePresenter<DetailView> {
    fun setMovieId(movieId: Int)
    fun loadData()
    fun loadVideos()
    fun loadRecommendations()
    fun loadCredit()
    fun loadReviews()
    fun loadMovieDetail()
}