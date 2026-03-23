package com.erdo.browser.mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var addressBar: EditText
    private lateinit var refreshLayout: SwipeRefreshLayout

    private val homeUrl = "https://www.google.com"
    private val mobileUserAgent =
        "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/131.0.0.0 Mobile Safari/537.36"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        addressBar = findViewById(R.id.addressBar)
        refreshLayout = findViewById(R.id.swipeRefresh)

        val back: ImageButton = findViewById(R.id.btnBack)
        val forward: ImageButton = findViewById(R.id.btnForward)
        val reload: ImageButton = findViewById(R.id.btnReload)
        val home: ImageButton = findViewById(R.id.btnHome)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadsImagesAutomatically = true
            builtInZoomControls = true
            displayZoomControls = false
            useWideViewPort = true
            loadWithOverviewMode = true
            cacheMode = WebSettings.LOAD_DEFAULT
            userAgentString = mobileUserAgent
        }

        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean = false

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                refreshLayout.isRefreshing = false
                addressBar.setText(url ?: "")
                updateNavButtons(back, forward)
            }
        }

        refreshLayout.setOnRefreshListener { webView.reload() }

        back.setOnClickListener { if (webView.canGoBack()) webView.goBack() }
        forward.setOnClickListener { if (webView.canGoForward()) webView.goForward() }
        reload.setOnClickListener { webView.reload() }
        home.setOnClickListener { webView.loadUrl(homeUrl) }

        addressBar.setOnEditorActionListener { _, actionId, event ->
            val enterPressed = event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEARCH || enterPressed) {
                navigate(addressBar.text.toString())
                true
            } else {
                false
            }
        }

        if (savedInstanceState == null) {
            webView.loadUrl(homeUrl)
        } else {
            webView.restoreState(savedInstanceState)
        }
    }

    private fun navigate(input: String) {
        val query = input.trim()
        if (query.isEmpty()) return

        val isLikelyUrl = URLUtil.isValidUrl(query) || query.contains(".")
        val targetUrl = if (isLikelyUrl) {
            if (query.startsWith("http://") || query.startsWith("https://")) query else "https://$query"
        } else {
            "https://www.google.com/search?q=${query.replace(" ", "+")}"
        }
        webView.loadUrl(targetUrl)
    }

    private fun updateNavButtons(back: ImageButton, forward: ImageButton) {
        back.isEnabled = webView.canGoBack()
        forward.isEnabled = webView.canGoForward()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }
}
