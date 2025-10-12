package com.merseyside.partyapp.presentation.view.fragment.statisticMember.model

import android.content.Context
import androidx.annotation.AttrRes
import androidx.databinding.Bindable
import com.merseyside.adapters.core.model.AdapterViewModel
import com.merseyside.archy.presentation.interfaces.IStringHelper
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.utils.doubleToStringPrice

class ResultItemViewModel(
    item: Result,
    private val currency: String
) : AdapterViewModel<Result>(item), IStringHelper {

    var isVisible: Boolean = true
    set(value) {
        field = value

        notifyPropertyChanged(BR.dividerVisible)
    }

    @Bindable
    fun getName(): String {
        return item.member.name
    }

    @Bindable
    fun getPrice(): String {
        return when (item) {
            is Result.ResultDebtor -> {
                getString(R.string.debt, doubleToStringPrice(item.price), currency)
            }

            is Result.ResultLender -> {
                getString(R.string.debit, doubleToStringPrice(item.price), currency)
            }
        }
    }

    @Bindable
    @AttrRes
    fun getTextColor(): Int {
        return when (item) {
            is Result.ResultLender -> android.R.attr.colorPrimary
            else -> android.R.attr.colorError
        }
    }

    @Bindable
    fun getDividerVisible(): Boolean {
        return isVisible
    }

    override fun getLocaleContext(): Context {
        return CalcApplication.getInstance()
    }
}