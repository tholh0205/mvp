package com.tholh.mvp.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatRatingBar
import com.tholh.mvp.R

class CustomRatingBar: AppCompatRatingBar {

    private val starDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_star)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (starDrawable != null) {
            setMeasuredDimension(
                starDrawable.intrinsicWidth * 5
                        + paddingLeft, starDrawable.intrinsicHeight
            )
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}