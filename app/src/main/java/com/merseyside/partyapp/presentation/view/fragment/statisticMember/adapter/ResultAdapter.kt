package com.merseyside.partyapp.presentation.view.fragment.statisticMember.adapter

import com.merseyside.partyapp.BR
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.model.ResultItemViewModel
import com.upstream.basemvvmimpl.presentation.adapter.BaseAdapter
import com.merseyside.partyapp.data.entity.Result
import com.upstream.basemvvmimpl.presentation.view.BaseViewHolder

class ResultAdapter : BaseAdapter<Result, ResultItemViewModel>() {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.view_result
    }

    override fun getBindingVariable(): Int {
        return BR.obj
    }

    override fun createItemViewModel(obj: Result): ResultItemViewModel {
        return ResultItemViewModel(obj, CalcApplication.getInstance().prefsHelper.getCurrency())
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        if (position == itemCount - 1) {
            getModelByPosition(position).isVisible = false
        }
    }
}