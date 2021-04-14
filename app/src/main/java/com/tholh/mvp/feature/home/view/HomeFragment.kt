package com.tholh.mvp.feature.home.view

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tholh.mvp.R
import com.tholh.mvp.feature.base.view.BaseFragment
import com.tholh.mvp.feature.home.Type
import com.tholh.mvp.feature.home.adapter.HomeAdapter
import com.tholh.mvp.feature.home.model.HomeItem
import com.tholh.mvp.feature.home.presenter.HomePresenter
import com.tholh.mvp.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment, HomeView {

    @Inject
    lateinit var homePresenter: HomePresenter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private var firstLoad = true
    private var homeAdapter: HomeAdapter? = null

    constructor() : super(R.layout.fragment_home)
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun getPresenter(): HomePresenter {
        return homePresenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            homePresenter.loadData()
        }
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.itemAnimator = null
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        if (homeAdapter == null) {
            homePresenter.loadData()
        } else {
            recyclerView.adapter = homeAdapter
        }
    }

    override fun showRefreshing(show: Boolean) {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun bindHomeData(homeData: List<HomeItem>) {
        homeAdapter = HomeAdapter()
        homeAdapter?.setHasStableIds(true)
        recyclerView.adapter = homeAdapter
        homeAdapter?.items = homeData
        homeAdapter?.notifyDataSetChanged()
        homeAdapter?.loadMoreListener = object : HomeAdapter.LoadMoreListener {
            override fun loadMore(id: Long) {
                when (id.toInt()) {
                    Type.TYPE_TRENDING -> {
                        //TRENDING
                        homePresenter.loadTrending()
                    }
                    Type.TYPE_POPULAR -> {
                        //POPULAR
                        homePresenter.loadPopular()
                    }
                    Type.TYPE_TOP_RATED -> {
                        //TOP RATED
                        homePresenter.loadTopRated()
                    }
                    Type.TYPE_UPCOMING -> {
                        //UPCOMING
                        homePresenter.loadUpcoming()
                    }
                }
            }
        }
        homeAdapter?.onMovieItemClickListener = object : HomeAdapter.OnMovieItemClickListener {
            override fun onMovieItemClick(view: View, movieId: Int?) {
                if (movieId == null) {
                    return
                }
                val args = Bundle()
                args.putInt("movieId", movieId)
                findNavController().navigate(
                    R.id.detailFragment,
                    args,
                    createNavOptionsBuilder().build()
                )
            }
        }
    }

    override fun showLoadMoreTrending(show: Boolean) {
        val viewHolder =
            recyclerView.findViewHolderForItemId(Type.TYPE_TRENDING.toLong()) as HomeAdapter.TrendingViewHolder
        viewHolder.trendingAdapter.showLoading = show
        viewHolder.trendingAdapter.notifyDataSetChanged()
    }

    override fun bindTrendingData(trending: List<Movie>?) {
        val viewHolder =
            recyclerView.findViewHolderForItemId(Type.TYPE_TRENDING.toLong()) as HomeAdapter.TrendingViewHolder
        viewHolder.trendingAdapter.items = trending
        viewHolder.trendingAdapter.notifyDataSetChanged()
    }

    override fun showLoadMorePopular(show: Boolean) {
        val viewHolder =
            recyclerView.findViewHolderForItemId(Type.TYPE_POPULAR.toLong()) as HomeAdapter.PopularViewHolder
        viewHolder.popularAdapter.showLoading = show
        viewHolder.popularAdapter.notifyDataSetChanged()
    }

    override fun bindPopularData(popularList: List<Movie>?) {
        val viewHolder =
            recyclerView.findViewHolderForItemId(Type.TYPE_POPULAR.toLong()) as HomeAdapter.PopularViewHolder
        viewHolder.popularAdapter.items = popularList
        viewHolder.popularAdapter.notifyDataSetChanged()
    }

    override fun showLoadMoreTopRated(show: Boolean) {
        val viewHolder =
            recyclerView.findViewHolderForItemId(Type.TYPE_TOP_RATED.toLong()) as HomeAdapter.TopRatedViewHolder
        viewHolder.topRatedAdapter.showLoading = show
        viewHolder.topRatedAdapter.notifyDataSetChanged()
    }

    override fun bindTopRatedData(topRatedList: List<Movie>?) {
        val viewHolder =
            recyclerView.findViewHolderForItemId(Type.TYPE_TOP_RATED.toLong()) as HomeAdapter.TopRatedViewHolder
        viewHolder.topRatedAdapter.items = topRatedList
        viewHolder.topRatedAdapter.notifyDataSetChanged()
    }

    override fun showLoadMoreUpcoming(show: Boolean) {
        val viewHolder =
            recyclerView.findViewHolderForItemId(Type.TYPE_UPCOMING.toLong()) as HomeAdapter.UpcomingViewHolder
        viewHolder.upcomingAdapter.showLoading = show
        viewHolder.upcomingAdapter.notifyDataSetChanged()
    }

    override fun bindUpcomingData(upcomingList: List<Movie>?) {
        val viewHolder =
            recyclerView.findViewHolderForItemId(Type.TYPE_UPCOMING.toLong()) as HomeAdapter.UpcomingViewHolder
        viewHolder.upcomingAdapter.items = upcomingList
        viewHolder.upcomingAdapter.notifyDataSetChanged()
    }
}