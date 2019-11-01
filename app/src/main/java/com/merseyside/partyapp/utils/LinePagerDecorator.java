package com.merseyside.partyapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merseyside.partyapp.R;

public class LinePagerDecorator extends RecyclerView.ItemDecoration {


    private int colorActive;
    private int colorInactive;
    private int colorTransition;
    private int lastActivePosition = 0;

    private static final float DP = Resources.getSystem().getDisplayMetrics().density;

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private final int mIndicatorHeight = (int) (DP * 4);

    /**
     *Inactive Indicator radiius.
     */
    private final float itemWidth = DP * 24;
    /**
     * Padding between indicators.
     */
    private final float mIndicatorItemPadding = DP * 12;

    /**
     * Some more natural animation interpolation
     */
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private final Paint mPaint = new Paint();

    public LinePagerDecorator(Context context) {

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);

        colorActive = value.data;

        context.getTheme().resolveAttribute(R.attr.colorPrimaryVariant, value, true);
        colorInactive = value.data;
        colorTransition =  ContextCompat.getColor(context, R.color.transparent);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = parent.getAdapter().getItemCount();
        if (itemCount > 1) {

            float totalLength = itemWidth * itemCount;
            float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
            float indicatorTotalWidth = totalLength + paddingBetweenItems;
            float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

            // center vertically in the allotted space
            float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);


            // find active page (which should be highlighted)
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int activePosition = layoutManager.findFirstVisibleItemPosition();
            if (activePosition == RecyclerView.NO_POSITION) {
                return;
            }

            // find offset of active page (if the user is scrolling)
            final View activeChild = layoutManager.findViewByPosition(activePosition);
            int left = activeChild.getLeft();
            int width = activeChild.getWidth();

            // on swipe the active item will be positioned from [-width, 0]
            // interpolate offset for smooth animation
            float progress = mInterpolator.getInterpolation(left * -1 / (float) width);

            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress);
        }
        // center horizontally, calculate width and subtract half from center
    }

    private void drawInactiveIndicators(Canvas c, float indicatorStartX, float cenY, int itemCount) {
        mPaint.setColor(colorInactive);
        mPaint.setStyle(Paint.Style.FILL);

        // width of item indicator including padding
        final float itemWidth = this.itemWidth + mIndicatorItemPadding;

        float cenX = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {
            // draw the circle for every item
            c.drawRect(cenX, cenY, cenX + this.itemWidth, cenY + mIndicatorHeight, mPaint);

            cenX += itemWidth;
        }
    }

    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                int highlightPosition, float progress) {
        // width of item indicator including padding
        final float itemWidth = this.itemWidth + mIndicatorItemPadding;
        if (progress == 0F) {
            // no swipe, draw a normal indicator
            mPaint.setColor(colorActive);
            mPaint.setStyle(Paint.Style.FILL);

            lastActivePosition = highlightPosition;
        } else {
            if (lastActivePosition > highlightPosition) {
                highlightPosition = lastActivePosition;
            }
            mPaint.setColor(colorTransition);
        }
        float highlightStart = indicatorStartX + itemWidth * highlightPosition;

        c.drawRect(highlightStart, indicatorPosY, highlightStart + this.itemWidth, indicatorPosY + mIndicatorHeight, mPaint);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = -mIndicatorHeight;
    }
}
