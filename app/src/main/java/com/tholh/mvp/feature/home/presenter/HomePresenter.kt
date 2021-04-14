package com.tholh.mvp.feature.home.presenter

import com.tholh.mvp.feature.base.presenter.BasePresenter
import com.tholh.mvp.feature.home.view.HomeView

interface HomePresenter : BasePresenter<HomeView> {
    fun loadData()

    fun loadTrending()
    fun loadPopular()
    fun loadTopRated()
    fun loadUpcoming()
}