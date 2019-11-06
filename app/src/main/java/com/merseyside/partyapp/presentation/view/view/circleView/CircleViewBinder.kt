package com.merseyside.partyapp.presentation.view.view.circleView

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("app:circleText")
fun setCircleText(view: CircleView, value: String?) {
    value?.let {
        view.setText(value)
    } ?: view.setText("")
}

@BindingAdapter("app:circleColor")
fun setCircleColor(view: CircleView, @ColorRes value: Int?) {
    if (value != null) {
        view.setColor(ContextCompat.getColor(view.context, value))
    }

}