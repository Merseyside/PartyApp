package com.pchmn.materialchips.views;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.R;
import com.pchmn.materialchips.R2;
import com.pchmn.materialchips.adapter.FilterableAdapter;
import com.pchmn.materialchips.model.ChipInterface;
import com.pchmn.materialchips.util.ViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterableListView extends RelativeLayout {

    private static final String TAG = FilterableListView.class.toString();
    private Context mContext;
    // list
    @BindView(R2.id.recycler_view) RecyclerView mRecyclerView;
    private FilterableAdapter mAdapter;
    private List<? extends ChipInterface> mFilterableList;
    // others
    private ChipsInput mChipsInput;

    private @IdRes int containerId;

    private boolean isBuildOver = false;

    public FilterableListView(Context context) {
        super(context);
        mContext = context;

        init();
    }

    public void setContainer(@IdRes int containerId) {
        this.containerId = containerId;
    }

    private void init() {
        // inflate layout
        View view = inflate(getContext(), R.layout.list_filterable_view, this);
        // butter knife
        ButterKnife.bind(this, view);

        // recycler
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        // hide on first
        setVisibility(GONE);
    }

    public void build(List<? extends ChipInterface> filterableList, ChipsInput chipsInput, ColorStateList backgroundColor, ColorStateList textColor) {
        mFilterableList = filterableList;
        mChipsInput = chipsInput;

        // adapter
        mAdapter = new FilterableAdapter(mContext, mRecyclerView, filterableList, chipsInput, backgroundColor, textColor);
        mRecyclerView.setAdapter(mAdapter);
        if(backgroundColor != null)
            mRecyclerView.getBackground().setColorFilter(backgroundColor.getDefaultColor(), PorterDuff.Mode.SRC_ATOP);

        // listen to change in the tree
        mChipsInput.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                if (getParent() != null) {
                    ((ViewGroup)getParent()).removeView(FilterableListView.this);
                }
                // position
                ViewGroup rootView;

                if (containerId == 0) {
                    rootView = (ViewGroup) mChipsInput.getRootView();
                } else {
                    rootView = mChipsInput.getRootView().findViewById(containerId);
                }

                if (rootView != null) {

                    // size
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            ViewUtil.getWindowWidth(mContext),
                            ViewGroup.LayoutParams.MATCH_PARENT);

                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                    if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        layoutParams.bottomMargin = ViewUtil.getNavBarHeight(mContext);
                    }


                    // add view
                    rootView.addView(FilterableListView.this, layoutParams);

                    // remove the listener:
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mChipsInput.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mChipsInput.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                    isBuildOver = true;
                }
            }
        });


    }

    public void filterList(CharSequence text) {
        if (isBuildOver) {
            mAdapter.getFilter().filter(text, count -> {
                // show if there are results
                if (mAdapter.getItemCount() > 0)
                    fadeIn();
                else
                    fadeOut();
            });
        }
    }

    /**
     * Fade in
     */
    public void fadeIn() {
        if(getVisibility() == VISIBLE)
            return;

        // get visible window (keyboard shown)
        final View rootView;

        if (containerId == 0) {
            rootView = getRootView();
        } else {
            rootView = getRootView().findViewById(containerId);
        }

        int[] rootCoord = calculateViewCoords(getRootView(), rootView);
        int[] globalRootCoords = calculateViewCoords(getRootView(), mChipsInput);

        ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
        if (globalRootCoords[1] < rootView.getMeasuredHeight() / 2) { // to bottom
            Rect rect = new Rect();
            int[] coord = calculateViewCoords(rootView, mChipsInput, rect);

            layoutParams.topMargin = coord[1] - rootCoord[1] + mChipsInput.getHeight();
            layoutParams.bottomMargin = getRootView().getHeight() - rect.bottom;
        } else { // to top

            layoutParams.bottomMargin = getRootView().getHeight() - globalRootCoords[1] - mChipsInput.getHeight();
        }

        setLayoutParams(layoutParams);

        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200);
        startAnimation(anim);
        setVisibility(VISIBLE);

    }

    private int[] calculateViewCoords(View rootView, View view) {
        return calculateViewCoords(rootView, view, new Rect());
    }

    private int[] calculateViewCoords(View rootView, View view, Rect rect) {
        rootView.getWindowVisibleDisplayFrame(rect);
        int[] coords = new int[2];
        view.getLocationInWindow(coords);

        return coords;
    }

    /**
     * Fade out
     */
    public void fadeOut() {
        if(getVisibility() == GONE)
            return;

        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(200);
        startAnimation(anim);
        setVisibility(GONE);
    }

    float convertDpToPixel(float dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return dp / density;
    }
}
