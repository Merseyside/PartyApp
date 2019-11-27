package com.merseyside.partyapp.presentation.view.activity.main.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.merseyside.partyapp.BR
import com.merseyside.partyapp.R
import com.merseyside.partyapp.databinding.ActivityMainBinding
import com.merseyside.partyapp.presentation.base.BaseCalcActivity
import com.merseyside.partyapp.presentation.di.component.DaggerMainComponent
import com.merseyside.partyapp.presentation.di.module.MainModule
import com.merseyside.partyapp.presentation.view.activity.main.model.MainViewModel
import com.merseyside.partyapp.presentation.view.activity.main.model.SharedViewModel
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

class MainActivity : BaseCalcActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var sharedViewModel: SharedViewModel

    private lateinit var navigator : Navigator

    override fun setBindingVariable(): Int {
        return BR.viewModel
    }

    override fun setLayoutId(): Int {
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

        if (savedInstanceState == null) {
            init()
        }
    }

    private fun initNavigation() {
        if (binding.container != null) {
            navigator = object : SupportAppNavigator(this, binding.container.id) {

                override fun applyCommand(command: Command?) {
                    super.applyCommand(command)
                    supportFragmentManager.executePendingTransactions()
                }

                override fun setupFragmentTransaction(
                    command: Command?,
                    currentFragment: Fragment?,
                    nextFragment: Fragment?,
                    fragmentTransaction: FragmentTransaction?
                ) {
                    super.setupFragmentTransaction(
                        command,
                        currentFragment,
                        nextFragment,
                        fragmentTransaction
                    )
                    fragmentTransaction!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                }
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
        return R.id.container
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
