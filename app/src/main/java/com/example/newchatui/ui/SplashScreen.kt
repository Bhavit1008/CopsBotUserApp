package com.example.newchatui.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.example.newchatui.R
import com.example.newchatui.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_date_loc.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 5000L
    var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Glide.with(this).asGif().load(R.raw.logo).into(image)

        Handler().postDelayed(
            {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }, SPLASH_TIME_OUT
        )
    }
}
