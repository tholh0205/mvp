package com.tholh.mvp.feature.detail.view

import com.tholh.mvp.feature.base.view.BaseView
import com.tholh.mvp.feature.detail.model.DetailItem
import com.tholh.mvp.model.Cast
import com.tholh.mvp.model.Recommendation
import com.tholh.mvp.model.Review
import com.tholh.mvp.model.Video

interface DetailView: BaseView {

    fun bindData(detailItemList: List<DetailItem>?)
    fun bindVideos(videos: List<Video>)
    fun bindReviews(reviews: List<Review>)
    fun bindCast(casts: List<Cast>)
    fun bindRecommendation(recommendations: List<Recommendation>)
}