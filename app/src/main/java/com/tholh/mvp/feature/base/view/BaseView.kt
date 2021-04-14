package com.tholh.mvp.feature.base.view

interface BaseView {

    fun showLoading()
    fun dismissLoading()
    fun showNetworkError()
    fun showGenericError()
}