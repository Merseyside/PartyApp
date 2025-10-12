package com.merseyside.partyapp.presentation.base

import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.activity.VMActivity
import com.merseyside.partyapp.CalcApplication

abstract class BaseCalcActivity<B : ViewDataBinding, M : BaseCalcViewModel> : VMActivity<B, M>() {

    val appComponent = CalcApplication.getInstance().appComponent

    override fun loadingObserver(isLoading: Boolean) {}
}