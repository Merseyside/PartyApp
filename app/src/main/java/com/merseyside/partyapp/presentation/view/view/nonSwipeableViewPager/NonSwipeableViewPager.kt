package com.merseyside.partyapp.presentation.view.view.nonSwipeableViewPager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager
import com.merseyside.partyapp.R

class NonSwipeableViewPager : ViewPager {

    private var duration: Int? = DEFAULT_DURATION

    constructor(context: Context) : super(context) {
        setMyScroller()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setMyScroller()
    }

    private fun loadAttributes(attrsSet: AttributeSet) {
        val array =
            context.theme.obtainStyledAttributes(attrsSet, R.styleable.NonSwipeableViewPager, 0, 0)

        duration = array.getInt(R.styleable.NonSwipeableViewPager_duration, DEFAULT_DURATION)

        array.recycle()
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    //down one is added for smooth scrolling
    private fun setMyScroller() {
        try {
            val viewPager = ViewPager::class.java
            val scroller = viewPager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller.set(this, MyScroller(context))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    inner class MyScroller internal constructor(context: Context) :
        Scroller(context, DecelerateInterpolator()) {

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, this.duration)
        }
    }

    companion object {
        private const val DEFAULT_DURATION = 350
    }
}