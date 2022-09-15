package com.example.websitetoappassignment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.websitetoappassignment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // TAG
    private val TAG = "MainActivityX"

    // View Binding
    private lateinit var binding: ActivityMainBinding

    // temp var for back click
    private var isBackClickTwice = false

    // timer
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            // bottom navigation view setup
            bottomNavigationViewSetUp()

            // web view setup
            webViewSetup()

        }
    }

    // web view setup
    private fun webViewSetup() {
        binding.apply {

            webView.apply {
                webViewClient = object : WebViewClient(){

                    override fun onPageCommitVisible(view: WebView?, url: String?) {
                        super.onPageCommitVisible(view, url)
                        progressIndicator.visibility = View.GONE
                    }

                }

                // some configs
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                // load first url as default
                loadUrl("https://www.webtonative.com")
            }

        }
    }

    // on back pressed
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                        isBackClickTwice = false
                    } else {
                        if (isBackClickTwice) {
                            finish()
                        } else {
                            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show()
                            isBackClickTwice = true
                            startTimer()
                        }
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    // start timer
    private fun startTimer(){
        if(timer != null) {
            timer!!.cancel()
        }

        timer = object: CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d(TAG,millisUntilFinished.toString())
            }

            override fun onFinish() {
                isBackClickTwice = false
            }
        }

        timer!!.start()
    }

    // bottom navigation view
    private fun bottomNavigationViewSetUp() {
        binding.apply {
            webView.apply {
                bottomNavigationView.setOnItemSelectedListener { menuItem ->
                    progressIndicator.visibility = View.VISIBLE
                    when (menuItem.itemId) {
                        R.id.nav_home -> {
                            loadUrl("https://www.webtonative.com")
                            true
                        }
                        R.id.nav_showcase -> {
                            loadUrl("https://www.webtonative.com/showcase")
                            true
                        }
                        R.id.nav_features -> {
                            loadUrl("https://www.webtonative.com/features")
                            true
                        }
                        R.id.nav_faq -> {
                            loadUrl("https://www.webtonative.com/faq")
                            true
                        }
                        R.id.nav_pricing -> {
                            loadUrl("https://www.webtonative.com/pricing")
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }

}