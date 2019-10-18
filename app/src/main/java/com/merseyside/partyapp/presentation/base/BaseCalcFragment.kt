package com.merseyside.partyapp.presentation.base

import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.merseyside.partyapp.CalcApplication
import com.upstream.basemvvmimpl.presentation.fragment.BaseMvvmFragment

abstract class BaseCalcFragment<B : ViewDataBinding, M : BaseCalcViewModel> : BaseMvvmFragment<B, M>() {

    val appComponent = CalcApplication.getInstance().appComponent

    fun goBack() {
        viewModel.goBack()
    }

    override fun clear() {}

    override fun loadingObserver(isLoading: Boolean) {}

    override fun onStart() {
        super.onStart()

        setTitleBackButtonEnabled()
    }

    private fun setTitleBackButtonEnabled() {
        if (getActionBar() != null) {
            getActionBar()!!.setDisplayHomeAsUpEnabled(hasTitleBackButton())

            if (hasTitleBackButton()) {
                setHasOptionsMenu(true)
            }
        }
    }

    @CallSuper
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            goBack()
        }

        return super.onOptionsItemSelected(item)
    }

    abstract fun hasTitleBackButton(): Boolean
}