package com.merseyside.partyapp.presentation.view.view.circleView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.ColorInt
import com.merseyside.partyapp.R
import kotlin.math.min

class CircleView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var size: Int = 0

    @ColorInt
    private var color: Int = Color.BLACK

    @ColorInt
    private var textColor: Int = Color.WHITE

    private var text: String = ""

    private val paint: Paint
    private val textPaint: Paint

    private val textSizeKoef = 0.33F

    private var rect: Rect

    init {

        loadAttrs(attributeSet)

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = textColor

        rect = Rect()
    }

    private fun loadAttrs(attributeSet: AttributeSet) {

        val array = context.theme.obtainStyledAttributes(attributeSet, R.styleable.CircleView, 0, 0)

        color = array.getColor(R.styleable.CircleView_color, color)
        text = array.getString(R.styleable.CircleView_text) ?: text
        textColor = array.getColor(R.styleable.CircleView_textColor, textColor)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        size = min(width, height)
        setMeasuredDimension(size, size)

        textPaint.textSize = convertPixelsToDp(size + (size * textSizeKoef).toInt())

        rect.set(0, 0, size, size)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            val point = (size / 2).toFloat()
            canvas.drawCircle(point, point, point, paint)

            drawCenter(canvas, rect, textPaint, text)
        }

    }

    private fun drawCenter(canvas: Canvas, rect: Rect, paint: Paint, text: String) {
        canvas.getClipBounds(rect)
        val cHeight = rect.height()
        val cWidth = rect.width()
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, rect)
        val x = cWidth / 2f - rect.width() / 2f - rect.left
        val y = cHeight / 2f + rect.height() / 2f - rect.bottom
        canvas.drawText(text, x, y, paint)
    }

    private fun convertPixelsToDp(px: Int): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun setText(text: String) {
        this.text = text

        invalidate()
    }

    fun setColor(@ColorInt color: Int) {
        this.color = color

        invalidate()
    }
}