package com.tholh.mvp.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.tholh.mvp.R


open class LoadableImageView : AppCompatImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun loadUrl(url: String?) {
        if (url.isNullOrEmpty()) {

        } else {
            Glide.with(this).load(url).into(this)
        }
    }

    fun loadPhotoRounded(url: String?) {
        if (!url.isNullOrBlank()) {
            Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(RoundedCorners(32)).into(this)
        }
    }

    fun loadPhotoCircle(url: String?) {
        if (!url.isNullOrBlank()) {
            Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop().into(this)
        }
    }


}