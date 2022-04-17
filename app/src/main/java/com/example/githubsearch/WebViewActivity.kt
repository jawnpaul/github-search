package com.example.githubsearch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubsearch.databinding.ActivityWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewActivity : AppCompatActivity() {
    private var _binding: ActivityWebViewBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
        val url = intent.getStringExtra(URL_TO_LOAD)
        url ?: finish()
        binding?.webview?.settings?.javaScriptEnabled = true
        url?.apply {
            binding?.webview?.loadUrl(url)
        }
        binding?.toolbar?.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val URL_TO_LOAD = "url_to_load"
        fun newIntent(url: String, context: Context): Intent {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(URL_TO_LOAD, url)
            return intent
        }
    }
}