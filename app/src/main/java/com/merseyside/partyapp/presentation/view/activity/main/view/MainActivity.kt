package com.merseyside.partyapp.presentation.view.activity.main.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.*
import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.databinding.ActivityMainBinding
import com.merseyside.partyapp.presentation.base.BaseCalcActivity
import com.merseyside.partyapp.presentation.di.component.DaggerMainComponent
import com.merseyside.partyapp.presentation.di.module.MainModule
import com.merseyside.partyapp.presentation.view.activity.main.model.MainViewModel
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import dev.chrisbanes.insetter.applyInsetter
import javax.inject.Inject

class MainActivity : BaseCalcActivity<ActivityMainBinding, MainViewModel>(), HasAd {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var sharedViewModel: SharedViewModel

    private lateinit var navigator: Navigator

    private var interstitialAd: InterstitialAd? = null

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun performInjection(bundle: Bundle?, vararg params: Any) {
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

        requireBinding().mainContainer.applyInsetter {
            type(statusBars = true) {
                margin()
            }

            type(navigationBars = true) { margin() }
        }
    }

    private fun initNavigation() {
        navigator = object : AppNavigator(this, requireBinding().container.id) {
            override fun applyCommands(commands: Array<out Command>) {
                super.applyCommands(commands)
                supportFragmentManager.executePendingTransactions()
            }

            override fun setupFragmentTransaction(
                screen: FragmentScreen,
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment
            ) {
                super.setupFragmentTransaction(
                    screen,
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

    override fun getMainToolbar(): Toolbar {
        return requireBinding().toolbar
    }

    private fun init() {
        viewModel.navigateToEventList()
    }

    override fun getFragmentContainer(): Int {
        return requireBinding().container.id
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

        requireBinding().adView.apply {
            loadAd(AdRequest.Builder().build())
        }

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            //"ca-app-pub-3940256099942544/1033173712", // test id
            getString(R.string.interstitialId),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Logger.log(TAG, adError.message)
                    interstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Logger.log(TAG, "Ad was loaded.")
                    this@MainActivity.interstitialAd = interstitialAd
                }
            })

//        interstitialAd.apply {
//            adUnitId = getString(R.string.interstitialId)
//            loadAd(AdRequest.Builder().build())
//        }

//        interstitialAd.adListener = object : AdListener() {
//            override fun onAdClosed() {
//                interstitialAd.loadAd(AdRequest.Builder().build())
//            }
//        }
    }

    override fun showRewardedAd() {
        throw NotImplementedError()
    }

    override fun showInterstitialAd() {
        interstitialAd?.show(this)

        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
                interstitialAd = null
            }
        }
    }

    override fun onRewardClosed() {
        throw NotImplementedError()
    }

    override fun setShowAdBanner(isShow: Boolean) {
        if (isShow) {
            requireBinding().adView.visibility = View.VISIBLE
        } else {
            requireBinding().adView.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
