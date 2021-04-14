package com.tholh.mvp.feature.base.presenter

import com.tholh.mvp.feature.base.view.BaseView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BasePresenterImpl<View : BaseView>(protected var view: View?) : BasePresenter<View>, CoroutineScope {

    protected val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + Dispatchers.IO

    override fun onViewCreated() {
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        view = null
        job.cancel()
    }
}