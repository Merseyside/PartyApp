package com.merseyside.partyapp.presentation.view.view.circleView

import androidx.annotation.AttrRes
import androidx.databinding.BindingAdapter
import com.upstream.basemvvmimpl.utils.getColorFromAttr

@BindingAdapter("app:circleText")
fun setCircleText(view: CircleView, value: String?) {
    value?.let {
        view.setText(value)
    } ?: view.setText("")
}

@BindingAdapter("app:circleColor")
fun setCircleColor(view: CircleView, @AttrRes value: Int?) {
    if (value != null) {
        view.setColor(view.context.getColorFromAttr(value))
    }
}

@BindingAdapter("app:circleTextColor")
fun setCircleTextColor(view: CircleView, @AttrRes value: Int?) {
    if (value != null) {
        view.setTextColor(view.context.getColorFromAttr(value))
    }
}