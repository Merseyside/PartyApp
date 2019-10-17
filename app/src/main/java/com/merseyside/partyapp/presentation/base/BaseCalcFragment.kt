package com.merseyside.partyapp.presentation.base

import androidx.databinding.ViewDataBinding
import com.merseyside.partyapp.CalcApplication
import com.upstream.basemvvmimpl.presentation.fragment.BaseMvvmFragment

abstract class BaseCalcFragment<B : ViewDataBinding, M : BaseCalcViewModel> : BaseMvvmFragment<B, M>() {

    val appComponent = CalcApplication.getInstance().appComponent

    fun goBack() {
        viewModel.back()
    }

    override fun clear() {}

    override fun loadingObserver(isLoading: Boolean) {}
}