package com.tholh.mvp.feature.detail.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tholh.mvp.R
import com.tholh.mvp.feature.base.presenter.BasePresenter
import com.tholh.mvp.feature.base.view.BaseFragment
import com.tholh.mvp.feature.base.view.BaseView
import com.tholh.mvp.feature.detail.adapter.*
import com.tholh.mvp.feature.detail.model.DetailItem
import com.tholh.mvp.feature.detail.presenter.DetailPresenter
import com.tholh.mvp.model.Cast
import com.tholh.mvp.model.Recommendation
import com.tholh.mvp.model.Review
import com.tholh.mvp.model.Video
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment: BaseFragment, DetailView {

    @Inject
    lateinit var detailPresenter: DetailPresenter

    private lateinit var recyclerView: RecyclerView
    private var detailAdapter : DetailAdapter? = null

    constructor() : super(R.layout.fragment_detail)
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun getPresenter(): DetailPresenter {
        return detailPresenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        if (args != null && args.containsKey("movieId")) {
            detailPresenter.setMovieId(args.getInt("movieId"))
        }
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        detailPresenter.loadData()
    }

    override fun bindData(detailItemList: List<DetailItem>?) {
        detailAdapter = DetailAdapter()
        detailAdapter?.setHasStableIds(true)
        detailAdapter?.items = detailItemList
        recyclerView.adapter = detailAdapter
    }

    override fun bindVideos(videos: List<Video>) {
        val videoAdapter = VideoAdapter()
        videoAdapter.items = videos
        recyclerView.adapter = videoAdapter
    }

    override fun bindReviews(reviews: List<Review>) {
        val reviewAdapter = ReviewAdapter()
        reviewAdapter.items = reviews
        recyclerView.adapter = reviewAdapter
    }

    override fun bindCast(casts: List<Cast>) {
        val castAdapter = CastAdapter()
        castAdapter.items = casts
        recyclerView.adapter = castAdapter
    }

    override fun bindRecommendation(recommendations: List<Recommendation>) {
        val recommendationAdapter = RecommendationAdapter()
        recommendationAdapter.items = recommendations
        recyclerView.adapter = recommendationAdapter
    }
}