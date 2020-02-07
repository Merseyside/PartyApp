package com.merseyside.partyapp.presentation.view.view.circleView

import androidx.annotation.AttrRes
import androidx.databinding.BindingAdapter
import com.merseyside.mvvmcleanarch.utils.ext.getColorFromAttr

@BindingAdapter("app:circleText")
fun setCircleText(view: CircleTextView, value: String?) {
    value?.let {
        view.setText(value)
    } ?: view.setText("")
}

@BindingAdapter("app:circleColor")
fun setCircleColor(view: CircleTextView, @AttrRes value: Int?) {
    if (value != null) {
        view.setColor(view.context.getColorFromAttr(value))
    }
}

@BindingAdapter("app:circleTextColor")
fun setCircleTextColor(view: CircleTextView, @AttrRes value: Int?) {
    if (value != null) {
        view.setTextColor(view.context.getColorFromAttr(value))
    }
}