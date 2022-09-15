package com.example.websitetoappassignment.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebBackForwardList
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.websitetoappassignment.R
import com.example.websitetoappassignment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // TAG
    private val TAG = "MainActivityX"

    // View Binding
    private lateinit var binding: ActivityMainBinding

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

    fun getBackForwardList() {
        val currentList: WebBackForwardList = binding.webView.copyBackForwardList()
        val currentSize = currentList.size
        for (i in 0 until currentSize) {
            val item = currentList.getItemAtIndex(i)
            val url = item.url
            Log.d(TAG, "The URL at index: " + Integer.toString(i) + " is " + url)
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
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadUrl("https://www.webtonative.com")
            }


        }
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
                            getBackForwardList()
                            true
                        }
                        else -> false
                    }
                }

            }
        }
    }

}