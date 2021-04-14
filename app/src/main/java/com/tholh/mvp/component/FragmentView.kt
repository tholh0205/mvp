package com.tholh.mvp.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import com.tholh.mvp.AndroidUtilities

class FragmentView : LinearLayoutCompat {

    var animationProcess: Float = 0f
        set(value) {
            field = value
            if (value < -1 || value > 1) {
                return
            }
            translationX = AndroidUtilities.displaySize.x * value
        }


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}