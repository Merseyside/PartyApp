package com.merseyside.partyapp.presentation.view.activity.main.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.gms.ads.*
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.databinding.ActivityMainBinding
import com.merseyside.partyapp.presentation.base.BaseCalcActivity
import com.merseyside.partyapp.presentation.di.component.DaggerMainComponent
import com.merseyside.partyapp.presentation.di.module.MainModule
import com.merseyside.partyapp.presentation.view.activity.main.model.MainViewModel
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import javax.inject.Inject

class MainActivity : BaseCalcActivity<ActivityMainBinding, MainViewModel>(), HasAd {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var sharedViewModel: SharedViewModel

    private lateinit var navigator: Navigator

    private val interstitialAd: InterstitialAd by lazy { InterstitialAd(this) }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun performInjection(bundle: Bundle?) {
        DaggerMainComponent.builder()
            .appComponent(appComponent)
            .mainModule(getMainModule(bundle))
            .build().inject(this)
    }

    private fun getMainModule(bundle: Bundle?): MainModule {
        return MainModule(this, bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigation()
        initAdMob()

        if (savedInstanceState == null) {
            init()
        }
    }

    private fun initNavigation() {
        navigator = object : AppNavigator(this, binding.container.id) {
            override fun applyCommands(commands: Array<out Command>) {
                super.applyCommands(commands)
                supportFragmentManager.executePendingTransactions()
            }

            override fun setupFragmentTransaction(
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment?
            ) {
                super.setupFragmentTransaction(
                    fragmentTransaction,
                    currentFragment,
                    nextFragment
                )
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        sharedViewModel.writeTo(outState)
    }

    override fun getToolbar(): Toolbar? {
        return binding.toolbar
    }

    private fun init() {
        viewModel.navigateToEventList()
    }

    override fun getFragmentContainer(): Int? {
        return binding.container.id
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun initAdMob() {
        MobileAds.initialize(this)

        binding.adView.apply {
            loadAd(AdRequest.Builder().build())
        }

        interstitialAd.apply {
            adUnitId = getString(R.string.interstitialId)
            loadAd(AdRequest.Builder().build())
        }

        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                interstitialAd.loadAd(AdRequest.Builder().build())
            }
        }
    }

    override fun showRewardedAd() {
        throw NotImplementedError()
    }

    override fun showInterstitialAd() {
        if (interstitialAd.isLoaded) {
            interstitialAd.show()
        }
    }

    override fun onRewardClosed() {
        throw NotImplementedError()
    }

    override fun setShowAdBanner(isShow: Boolean) {
        if (isShow) {
            binding.adView.visibility = View.VISIBLE
        } else {
            binding.adView.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
