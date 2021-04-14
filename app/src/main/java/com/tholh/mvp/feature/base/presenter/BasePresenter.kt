package com.tholh.mvp.feature.base.presenter

import com.tholh.mvp.feature.base.view.BaseView

interface BasePresenter<View : BaseView> {
    fun onViewCreated()
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
}