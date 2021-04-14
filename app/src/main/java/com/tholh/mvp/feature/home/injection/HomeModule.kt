package com.tholh.mvp.feature.home.injection

import androidx.fragment.app.Fragment
import com.tholh.mvp.feature.home.presenter.HomePresenter
import com.tholh.mvp.feature.home.presenter.HomePresenterImpl
import com.tholh.mvp.feature.home.view.HomeFragment
import com.tholh.mvp.feature.home.view.HomeView
import com.tholh.mvp.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit

@InstallIn(FragmentComponent::class)
@Module
object HomeModule {

    @Provides
    fun provideHomeFragment(fragment: Fragment) : HomeFragment {
        return fragment as HomeFragment
    }
}

@InstallIn(FragmentComponent::class)
@Module
class HomeFragmentModule {

    @Provides
    fun provideHomeView(homeFragment: HomeFragment) : HomeView {
        return homeFragment
    }

    @Provides
    fun provideHomePresenter(homeView: HomeView, movieRepository: MovieRepository) : HomePresenter {
        return HomePresenterImpl(homeView, movieRepository)
    }


}