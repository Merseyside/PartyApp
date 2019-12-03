package com.merseyside.partyapp.presentation.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.merseyside.partyapp.CalcApplication
import com.merseyside.mvvmcleanarch.presentation.fragment.BaseMvvmFragment
import com.merseyside.mvvmcleanarch.presentation.view.IFocusManager
import com.merseyside.partyapp.R
import com.merseyside.partyapp.presentation.view.activity.main.view.HasAd
import com.merseyside.partyapp.utils.PrefsHelper
import javax.inject.Inject

abstract class BaseCalcFragment<B : ViewDataBinding, M : BaseCalcViewModel> : BaseMvvmFragment<B, M>(), IFocusManager {

    @Inject
    lateinit var prefsHelper: PrefsHelper

    val appComponent = CalcApplication.getInstance().appComponent
    private lateinit var adView: HasAd

    private val interstitialObserver = Observer<Boolean> {
        adView.showInterstitialAd()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is HasAd) {
            adView = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keepOneFocusedView()

        viewModel.interstitialLiveEvent.observe(this, interstitialObserver)
    }

    private fun goBack() {
        viewModel.goBack()
    }

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

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.interstitialLiveEvent.removeObserver(interstitialObserver)
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

    fun shareStatistic(text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                text
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    fun showRateUsDialog() {
        showAlertDialog(
            title = getActualString(R.string.rate_title),
            message = getActualString(R.string.rate_description),
            positiveButtonText = getActualString(R.string.rate_yes),
            negativeButtonText = getActualString(R.string.rate_no),
            onPositiveClick = {
                prefsHelper.setRated(true)

                //code to launch google play in browser
            }
        )
    }

    fun logEvent(event: String, bundle: Bundle) {
        CalcApplication.getInstance().logFirebaseEvent(event, bundle)
    }

    companion object {
        private const val TAG = "BaseCalcFragment"
    }

}