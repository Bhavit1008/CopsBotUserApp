package com.example.newchatui.features.awarnessPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.newchatui.R

class TwitterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter)
        val myWebView: WebView = findViewById(R.id.twitterWebView)
        myWebView.loadUrl("https://tweetfeeds.000webhostapp.com/")
        myWebView.settings.javaScriptEnabled = true

    }
}
