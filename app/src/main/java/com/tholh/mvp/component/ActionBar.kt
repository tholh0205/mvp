package com.tholh.mvp.component

import android.R
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import com.tholh.mvp.AndroidUtilities


class ActionBar : FrameLayout {
    private var actionBarHeight: Int

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setPadding(0, AndroidUtilities.statusBarHeight, 0, 0)
        val tv = TypedValue()
        context.theme.resolveAttribute(R.attr.actionBarSize, tv, true)
         actionBarHeight = resources.getDimensionPixelSize(tv.resourceId)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(actionBarHeight + AndroidUtilities.statusBarHeight, MeasureSpec.EXACTLY))
    }
}