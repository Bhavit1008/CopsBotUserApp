package com.example.newchatui.ui.chat.awarness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.newchatui.R
import com.example.newchatui.ui.chat.IntroActivity
import com.example.newchatui.ui.DashBoardActivity
import io.github.ponnamkarthik.richlinkpreview.ViewListener
import kotlinx.android.synthetic.main.activity_cyber_crime_safety_measures.*

class CyberCrimeSafetyMeasures : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cyber_crime_safety_measures)
        Glide.with(this).asGif().load(R.raw.logo).into(botLogo)

        richLinkView.setLink("https://youtu.be/8tR9P4QX82I", object :
            ViewListener {

            override fun onSuccess(status: Boolean) {

            }

            override fun onError(e: Exception) {

            }
        })

        btnRegister.setOnClickListener {
            val intent =Intent(this,IntroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,DashBoardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )

    }
}
