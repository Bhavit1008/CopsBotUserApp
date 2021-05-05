package com.example.newchatui.ui.chat.awarness

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.newchatui.R
import com.example.newchatui.ui.DashBoardActivity
import io.github.ponnamkarthik.richlinkpreview.ViewListener
import kotlinx.android.synthetic.main.activity_women_safety.*
import kotlinx.android.synthetic.main.activity_women_safety.richLinkView

class WomenSafety : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_women_safety)
        Glide.with(this).asGif().load(R.raw.logo).into(botLogo)

        richLinkView.setLink("https://youtu.be/scvi2EemtDw", object :
            ViewListener {

            override fun onSuccess(status: Boolean) {

            }

            override fun onError(e: Exception) {

            }
        })
        chipEmergencyWomen.setOnClickListener {
            val u: Uri = Uri.parse("tel:" + "1091")
            val i = Intent(Intent.ACTION_DIAL, u)
            try {
                // Launch the Phone app's dialer with a phone
                // number to dial a call.
                startActivity(i)
            } catch (s: SecurityException) {
                // show() method display the toast with
                // exception message.
                Toast.makeText(this, s.message, Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, DashBoardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
    }
}
