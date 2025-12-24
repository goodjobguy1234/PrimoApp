package com.example.primohomepage.feature.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.primohomepage.R
import com.example.primohomepage.databinding.ActivityWebViewBinding

class DetailWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private val url by lazy {
        intent.getStringExtra(URL_BUNDLE) ?: "https://www.google.co.th/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initializeView() {

        with(binding.webView) {
            settings.javaScriptEnabled = false
            settings.loadsImagesAutomatically = true

            webChromeClient = WebChromeClient()
            loadUrl(this@DetailWebViewActivity.url)
        }
    }

    companion object {
        const val URL_BUNDLE = "url"
        fun openScreen(context: Context, urlToLoad: String) {
            val intent = Intent(context, DetailWebViewActivity::class.java).apply {
                putExtra(URL_BUNDLE, urlToLoad)
            }
            context.startActivity(intent)
        }
    }
}