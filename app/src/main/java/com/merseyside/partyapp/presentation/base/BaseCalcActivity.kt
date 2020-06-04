package com.merseyside.partyapp.presentation.base

import androidx.databinding.ViewDataBinding
import com.merseyside.merseyLib.presentation.activity.BaseVMActivity
import com.merseyside.partyapp.CalcApplication

abstract class BaseCalcActivity<B : ViewDataBinding, M : BaseCalcViewModel> : BaseVMActivity<B, M>() {

    val appComponent = CalcApplication.getInstance().appComponent

    override fun loadingObserver(isLoading: Boolean) {}
}