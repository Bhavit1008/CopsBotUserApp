package com.example.newchatui.features.awarnessPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.newchatui.R

class RoadSafetyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_road_safety_awarness)
        val myWebView: WebView = findViewById(R.id.roadSafetyWebView)
        myWebView.loadUrl("https://road-safety-awareness.000webhostapp.com/")
        myWebView.settings.javaScriptEnabled = true
    }
}
