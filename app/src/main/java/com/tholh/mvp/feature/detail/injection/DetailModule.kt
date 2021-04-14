package com.tholh.mvp.feature.detail.injection

import androidx.fragment.app.Fragment
import com.tholh.mvp.feature.detail.presenter.DetailPresenter
import com.tholh.mvp.feature.detail.presenter.DetailPresenterImpl
import com.tholh.mvp.feature.detail.view.DetailFragment
import com.tholh.mvp.feature.detail.view.DetailView
import com.tholh.mvp.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
object DetailModule {

    @Provides
    fun provideDetailFragment(fragment: Fragment) : DetailFragment {
        return fragment as DetailFragment
    }
}

@InstallIn(FragmentComponent::class)
@Module
class DetailFragmentModule {

    @Provides
    fun provideDetailView(detailFragment: DetailFragment) : DetailView {
        return detailFragment
    }

    @Provides
    fun provideDetailPresenter(detailView: DetailView, movieRepository: MovieRepository) : DetailPresenter {
        return DetailPresenterImpl(detailView, movieRepository)
    }
}