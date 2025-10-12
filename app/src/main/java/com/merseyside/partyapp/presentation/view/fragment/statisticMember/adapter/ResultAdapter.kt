package com.merseyside.partyapp.presentation.view.fragment.statisticMember.adapter

import com.merseyside.adapters.SimpleAdapter
import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.adapters.core.holder.ViewHolder
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.presentation.view.fragment.statisticMember.model.ResultItemViewModel
import com.merseyside.partyapp.data.entity.Result

class ResultAdapter(adapterConfig: AdapterConfig<Result, ResultItemViewModel>) : SimpleAdapter<Result, ResultItemViewModel>(adapterConfig) {

    override fun getLayoutIdForViewType(viewType: Int): Int {
        return R.layout.view_result
    }

    override fun getBindingVariable() = BR.obj

    override fun createItemViewModel(item: Result): ResultItemViewModel {
        return ResultItemViewModel(item, CalcApplication.getInstance().prefsHelper.getCurrency())
    }

    override fun onBindViewHolder(holder: ViewHolder<Result, ResultItemViewModel>, position: Int) {
        super.onBindViewHolder(holder, position)

        if (position == itemCount - 1) {
            getModelByPosition(position).isVisible = false
        }
    }

    companion object {
        operator fun invoke(): ResultAdapter {
            return initAdapter(::ResultAdapter)
        }
    }
}