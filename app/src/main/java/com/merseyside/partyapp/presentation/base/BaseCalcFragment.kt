package com.merseyside.partyapp.presentation.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.merseyside.archy.presentation.fragment.BaseVMFragment
import com.merseyside.archy.presentation.view.IFocusManager
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.presentation.view.activity.main.view.HasAd
import com.merseyside.partyapp.utils.PrefsHelper
import com.merseyside.utils.fragment.onBackPressedDispatcher.setOnBackPressedCallback
import javax.inject.Inject
import androidx.core.net.toUri
import com.merseyside.partyapp.BuildConfig


abstract class BaseCalcFragment<B : ViewDataBinding, M : BaseCalcViewModel> : BaseVMFragment<B, M>(), IFocusManager {

    @Inject
    lateinit var prefsHelper: PrefsHelper

    val appComponent = CalcApplication.getInstance().appComponent
    private lateinit var adView: HasAd

    protected lateinit var onBackPressedCallback: OnBackPressedCallback

    private val interstitialObserver = Observer<Boolean> {
        showInterstitial()
    }

    private fun showInterstitial() {
        if (BuildConfig.FLAVOR == "user") {
            adView.showInterstitialAd()
        }
    }

    override fun isAppBarNavigateUpEnabled(): Boolean {
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is HasAd) {
            adView = context
        }

        addOnBackPressedCallback()
    }

    private fun addOnBackPressedCallback() {
        onBackPressedCallback = setOnBackPressedCallback {
            if (!onBackPressed()) {
                navigateUp()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keepOneFocusedView()
        viewModel.interstitialLiveEvent.observe(this, interstitialObserver)
    }

    override fun navigateUp() {
        viewModel.goBack()
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    override fun loadingObserver(isLoading: Boolean) {}

    override fun onStart() {
        super.onStart()

        adView.setShowAdBanner(isShowAdBanner())
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.interstitialLiveEvent.removeObserver(interstitialObserver)
    }

    override fun getToolbar(): Toolbar? {
        return null
    }

    override fun getRootView(): View {
        return requireView()
    }

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

        showInterstitial()
    }

    fun showRateUsDialog() {
        showAlertDialog(
            title = getActualString(R.string.rate_title),
            message = getActualString(R.string.rate_description),
            positiveButtonText = getActualString(R.string.rate_yes),
            negativeButtonText = getActualString(R.string.rate_no),
            onPositiveClick = {
                prefsHelper.setRated(true)

                goToGooglePlay()
            }
        )
    }
    private fun goToGooglePlay() {
        val url = "https://play.google.com/store/apps/details?id=com.merseyside.partyapp"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = url.toUri()
        startActivity(i)
    }

    fun logEvent(event: String, bundle: Bundle) {
        CalcApplication.getInstance().logFirebaseEvent(event, bundle)
    }

    open fun isShowAdBanner(): Boolean {
        return true
    }
}