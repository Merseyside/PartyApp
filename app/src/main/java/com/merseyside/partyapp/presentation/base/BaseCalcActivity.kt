package com.merseyside.partyapp.presentation.base

import androidx.databinding.ViewDataBinding
import com.merseyside.partyapp.CalcApplication
import com.merseyside.mvvmcleanarch.presentation.activity.BaseMvvmActivity

abstract class BaseCalcActivity<B : ViewDataBinding, M : BaseCalcViewModel> : BaseMvvmActivity<B, M>() {

    val appComponent = CalcApplication.getInstance().appComponent

    override fun loadingObserver(isLoading: Boolean) {}
}