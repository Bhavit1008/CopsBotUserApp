package com.example.newchatui.ui

import android.R.attr.x
import android.R.attr.y
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newchatui.R
import com.example.newchatui.features.ReportIncidentActivity
import com.example.newchatui.features.alerts.AlertsActivity
import com.example.newchatui.features.awarnessPages.TwitterActivity
import com.example.newchatui.features.statusTracking.StatusTrackingActivity
import com.example.newchatui.ui.chat.IntroActivity
import kotlinx.android.synthetic.main.activity_dash_board.*
import java.util.*


@Suppress("DEPRECATED")
class DashBoardActivity : AppCompatActivity() {
    var language : String = ""
    var sharedName : String = "sharedPreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        if(sharedPreferences!=null){
            language = sharedPreferences.getString("language", "name")
            if(language == "Hindi")
                language = "hi"
            if(language == "English")
                language = "en"
        }
        Toast.makeText(applicationContext,language,Toast.LENGTH_SHORT).show()
        setLocale(language)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

            cardChatbot.setOnClickListener{
            val intent = Intent(this,IntroActivity::class.java)
            startActivity(intent)
        }

        cardAlerts.setOnClickListener {
            val intent = Intent(this,AlertsActivity::class.java)
            startActivity(intent)
        }

        cardNearbyPoliceStation.setOnClickListener {
            var x ="24.6126"
            var y = "73.7022"
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "http://maps.google.com/maps?&saddr="
                            + x
                            + ","
                            + y
                            + "&daddr=nearby police stations"
                )
            )
            startActivity(intent)

        }

        cardReportIncident.setOnClickListener {
            val intent = Intent(this,ReportIncidentActivity::class.java)
            startActivity(intent)
        }

        cardStatustracking.setOnClickListener {
            val intent = Intent(this,StatusTrackingActivity::class.java)
            startActivity(intent)
        }

        cardTwitterFeeds.setOnClickListener {
            val intent = Intent(this,TwitterActivity::class.java)
            startActivity(intent)
        }

        chipSOS.setOnClickListener {
            sendSMS()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res = resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
    }


    fun sendSMS()
    {
        val uri = Uri.parse("smsto:7726062540")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", "Help me immediatly i am stuck at http://maps.google.com/maps?q=24.6126,73.7022")
        startActivity(intent)
    }
}
