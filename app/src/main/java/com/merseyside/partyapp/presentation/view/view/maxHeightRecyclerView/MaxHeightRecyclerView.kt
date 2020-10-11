package com.merseyside.partyapp.presentation.view.view.maxHeightRecyclerView

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.partyapp.R


class MaxHeightRecyclerView(context: Context, attrRes: AttributeSet) : RecyclerView(context, attrRes) {
    private var mMaxHeight = 0

    init {
        loadAttributes(attrRes)
    }

    private fun loadAttributes(
        attrs: AttributeSet?
    ) {
        val arr: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView)
        mMaxHeight = arr.getLayoutDimension(R.styleable.MaxHeightRecyclerView_maxHeight, mMaxHeight)
        arr.recycle()
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        var heightMeasureSpecMut = heightMeasureSpec
        if (mMaxHeight > 0) {
            Log.d(TAG, "$mMaxHeight")
            heightMeasureSpecMut = MeasureSpec.makeMeasureSpec(
                mMaxHeight,
                MeasureSpec.AT_MOST
            )
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpecMut)
    }

    companion object {
        private const val TAG = "MaxHeightRecyclerView"
    }

}