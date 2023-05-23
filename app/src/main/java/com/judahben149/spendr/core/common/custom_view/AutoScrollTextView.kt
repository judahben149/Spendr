package com.judahben149.spendr.core.common.custom_view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.Scroller
import androidx.appcompat.widget.AppCompatTextView

class AutoScrollTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val scrollingSpeed = 15 // Adjust the scrolling speed as desired

    override fun isFocused(): Boolean {
        return true
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect)
        }
    }

    override fun setScroller(scroller: Scroller) {
        scroller.forceFinished(true)
        scroller.startScroll(0, 0, scrollingSpeed, 0, -1)
        invalidate()
    }
}