package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import androidx.annotation.AttrRes
import androidx.databinding.Bindable
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.Order
import com.upstream.basemvvmimpl.presentation.model.BaseAdapterViewModel
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.utils.doubleToStringPrice

class ResultItemViewModel(override var obj: Result) : BaseAdapterViewModel<Result>(obj) {

    override fun areItemsTheSame(obj: Result): Boolean {
        return this.obj == obj
    }

    override fun notifyUpdate() {
    }

    @Bindable
    fun getName(): String {
        return obj.member.name
    }

    @Bindable
    fun getPrice(): String {
        return when (obj) {
            is Result.ResultDebtor -> {
                CalcApplication.getInstance().getActualString(R.string.debt, doubleToStringPrice(obj.price))
            }

            is Result.ResultLender -> {
                CalcApplication.getInstance().getActualString(R.string.debit, doubleToStringPrice(obj.price))
            }
        }
    }

    @Bindable
    @AttrRes
    fun getBackgroundColor(): Int {
        return when (obj) {
            is Result.ResultLender -> {
                R.attr.colorPrimary
            }
            else -> {
                R.attr.colorError
            }
        }
    }

    @Bindable
    @AttrRes
    fun getTextColor(): Int {
        return when (obj) {
            is Result.ResultLender -> {
                R.attr.colorPrimary
            }
            else -> {
                R.attr.colorError
            }
        }
    }
}