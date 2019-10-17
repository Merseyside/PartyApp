package com.merseyside.partyapp.presentation.base

import android.content.Context
import androidx.annotation.StringRes
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.presentation.exception.ErrorMessageFactory
import com.upstream.basemvvmimpl.presentation.model.BaseViewModel
import ru.terrakok.cicerone.Router

abstract class BaseCalcViewModel(private val router: Router? = null) : BaseViewModel() {

    protected val context = CalcApplication.getInstance()
    protected val errorMsgCreator = ErrorMessageFactory(context)

    protected fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    fun back() {
        router?.exit()
    }

    override fun updateLanguage(context: Context) {}
}