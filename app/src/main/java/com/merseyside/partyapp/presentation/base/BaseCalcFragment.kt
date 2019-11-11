package com.merseyside.partyapp.presentation.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import com.merseyside.partyapp.CalcApplication
import com.upstream.basemvvmimpl.presentation.fragment.BaseMvvmFragment
import com.upstream.basemvvmimpl.presentation.view.IFocusManager

abstract class BaseCalcFragment<B : ViewDataBinding, M : BaseCalcViewModel> : BaseMvvmFragment<B, M>(), IFocusManager {

    val appComponent = CalcApplication.getInstance().appComponent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keepOneFocusedView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        viewModel.writeTo(outState)
    }

    protected fun goBack() {
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

    override fun getToolbar(): Toolbar? {
        return null
    }

    override fun getRootView(): View {
        return view!!
    }

    abstract fun hasTitleBackButton(): Boolean

}