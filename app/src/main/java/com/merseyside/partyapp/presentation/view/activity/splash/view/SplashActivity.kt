package com.merseyside.partyapp.presentation.view.activity.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import com.merseyside.merseyLib.presentation.activity.BaseActivity
import com.merseyside.partyapp.R
import com.merseyside.partyapp.presentation.view.activity.main.view.MainActivity
import com.merseyside.merseyLib.utils.UpdateManager

class SplashActivity : BaseActivity() {

    private val updateManager: UpdateManager by lazy {
        UpdateManager(this)
    }

    private lateinit var handler: Handler

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getToolbar(): Toolbar? {
        return null
    }

    override fun getFragmentContainer(): Int? {
        return null
    }

    override fun performInjection(bundle: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        updateManager.setOnAppUpdateListener(object: UpdateManager.OnAppUpdateListener {

            override fun immediateUpdateAvailable() {
                stopTimer()
                updateManager.startImmediateUpdate(UPDATE_REQUEST_CODE)
            }

            override fun flexibleUpdateAvailable() {
                stopTimer()
                updateManager.startImmediateUpdate(UPDATE_REQUEST_CODE)
            }

            override fun updateDownloaded() {
                stopTimer()
                updateManager.installDownloadedUpdate()
            }
        })

        startTimer()
    }

    private val runnable = Runnable {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

    private fun startTimer() {
        handler = Handler().apply { postDelayed(runnable, SPLASH_TIME) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UPDATE_REQUEST_CODE) {
            when(resultCode) {
                RESULT_IN_APP_UPDATE_FAILED -> {
                    Log.d(TAG, "Update flow failed!")
                    updateManager.startImmediateUpdate(UPDATE_REQUEST_CODE)
                }

                else -> startTimer()
            }
        }
    }

    companion object {
        private const val TAG = "SplashActivity"

        private const val SPLASH_TIME = 600L

        private const val UPDATE_REQUEST_CODE = 28100

        private const val RESULT_IN_APP_UPDATE_FAILED = 1
    }
}