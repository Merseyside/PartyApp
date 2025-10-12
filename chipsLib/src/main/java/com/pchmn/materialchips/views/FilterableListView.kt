package com.pchmn.materialchips.views

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AlphaAnimation
import android.widget.Filter
import android.widget.RelativeLayout
import androidx.annotation.IdRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.utils.binding.viewBinding
import com.merseyside.utils.view.ext.setMargin
import com.pchmn.materialchips.ChipsInput
import com.pchmn.materialchips.R
import com.pchmn.materialchips.adapter.FilterableAdapter
import com.pchmn.materialchips.databinding.ListFilterableViewBinding
import com.pchmn.materialchips.model.ChipInterface
import com.pchmn.materialchips.util.ViewUtil

class FilterableListView(private val mContext: Context) : RelativeLayout(mContext) {

    private val binding by viewBinding<ListFilterableViewBinding>(R.layout.list_filterable_view)

    val mRecyclerView: RecyclerView by lazy { binding.recyclerView }
    private lateinit var mAdapter: FilterableAdapter
    private lateinit var mFilterableList: List<ChipInterface?>

    // others
    private lateinit var mChipsInput: ChipsInput

    @IdRes
    private var containerId = 0

    private var containerView: RelativeLayout? = null

    private var isBuildOver = false

    init {
        init()
    }

    fun setContainer(@IdRes containerId: Int) {
        this.containerId = containerId
    }

    private fun init() {

        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        setMargin(convertDpToPixel(16f).toInt())

        // recycler
        mRecyclerView.layoutManager = LinearLayoutManager(
            mContext,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    fun build(
        filterableList: List<ChipInterface?>,
        chipsInput: ChipsInput,
        backgroundColor: ColorStateList?,
        textColor: ColorStateList?
    ) {
        mFilterableList = filterableList
        mChipsInput = chipsInput

        // adapter
        mAdapter = FilterableAdapter(
            mContext,
            mRecyclerView,
            filterableList,
            chipsInput,
            backgroundColor,
            textColor
        )
        mRecyclerView!!.adapter = mAdapter
        if (backgroundColor != null) mRecyclerView!!.background.setColorFilter(
            backgroundColor.getDefaultColor(),
            PorterDuff.Mode.SRC_ATOP
        )

        // listen to change in the tree
        mChipsInput.getViewTreeObserver()
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (containerView != null) {
                        containerView!!.removeView(this@FilterableListView)
                    }

                    if (containerView == null) {
                        containerView = if (containerId == 0) {
                            mChipsInput.getRootView()
                        } else {
                            mChipsInput.getRootView().findViewById<ViewGroup>(containerId)
                        } as? RelativeLayout
                    }

                    if (containerView != null) {
                        val layoutParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        if (mContext.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            layoutParams.bottomMargin = ViewUtil.getNavBarHeight(mContext)
                        }

                        Log.d("lel", "${layoutParams.width}")

                        // add view
                        containerView!!.addView(this@FilterableListView, layoutParams)
                        //containerView!!.visibility = View.VISIBLE

                        // remove the listener:
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            mChipsInput.getViewTreeObserver().removeGlobalOnLayoutListener(this)
                        } else {
                            mChipsInput.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                        }

                        isBuildOver = true
                    }
                }
            })
    }

    fun filterList(text: CharSequence?) {
        if (isBuildOver) {
            mAdapter.getFilter().filter(text, Filter.FilterListener { count: Int ->
                // show if there are results
                if (mAdapter.getItemCount() > 0) fadeIn()
                else fadeOut()
            })
        }
    }

    /**
     * Fade in
     */
    fun fadeIn() {
        if (containerView!!.getVisibility() == View.VISIBLE) return

        val anim: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        anim.setDuration(200)
        containerView!!.startAnimation(anim)
        containerView!!.setVisibility(View.VISIBLE)
    }

    private fun calculateViewCoords(rootView: View, view: View, rect: Rect = Rect()): IntArray {
        rootView.getWindowVisibleDisplayFrame(rect)
        val coords = IntArray(2)
        view.getLocationInWindow(coords)

        return coords
    }

    /**
     * Fade out
     */
    fun fadeOut() {
        if (containerView?.getVisibility() == View.GONE) return

        val anim: AlphaAnimation = AlphaAnimation(1.0f, 0.0f)
        anim.setDuration(200)
        containerView?.startAnimation(anim)
        containerView?.setVisibility(View.GONE)
    }

    fun convertDpToPixel(dp: Float): Float {
        val density: Float = getContext().getResources().getDisplayMetrics().density
        return dp / density
    }

    companion object {
        private val TAG = FilterableListView::class.java.toString()
    }
}
