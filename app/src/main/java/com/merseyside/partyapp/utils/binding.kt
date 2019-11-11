package com.merseyside.partyapp.utils

import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.databinding.BindingAdapter
import com.upstream.basemvvmimpl.utils.getColorFromAttr


@BindingAdapter("app:attrTextColor")
fun setCustomTextColor(view: TextView, @AttrRes attrId: Int) {
    view.setTextColor(view.context.getColorFromAttr(attrId))
}