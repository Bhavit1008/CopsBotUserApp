package com.example.newchatui.ui.chat.awarness

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newchatui.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_cyber_crime_category.*

class CyberCrimeCategory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cyber_crime_category)
        Glide.with(this).asGif().load(R.raw.logo).into(botLogo)

        chipGroupCyberCrimeCategory.setOnCheckedChangeListener { group, checkedId: Int ->
            // Get the checked chip instance from chip group
            val chip: Chip? = findViewById(checkedId)

            chip?.let {
                // Show the checked chip text on toast message
                chipGroupOptions.visibility = View.VISIBLE
                txtAwarnessCyberCrimeCategory.visibility = View.VISIBLE
                Toast.makeText(applicationContext,it.text, Toast.LENGTH_SHORT).show()
                if(it.text.toString() == "Cyber Bullying")
                {
                    txtAwarnessCyberCrimeCategory.text = "A form of harassment or bullying inflicted through the use of electronic or communication devices such as computers, mobile phones, laptops, etc."
                }

                if(it.text.toString() == "Online Job Fraud")
                {
                    txtAwarnessCyberCrimeCategory.text = "Online Job Fraud is an attempt to defraud people who are in need of employment by giving them a false hope/ promise of better employment with higher wages.\n"
                }

                if(it.text.toString() == "Vishing")
                {
                    txtAwarnessCyberCrimeCategory.text = "Vishing is an attempt where fraudsters try to seek personal information like Customer ID, Net Banking password, ATM PIN, OTP, Card expiry date, CVV, etc. through a phone call."
                }

                if(it.text.toString() == "Sim Swap Scam")
                {
                    txtAwarnessCyberCrimeCategory.text = "SIM Swap Scam occurs when fraudsters manage to get a new SIM card issued against a registered mobile number fraudulently through the mobile service provider. With the help of this new SIM card, they get One Time Password (OTP) and alerts, required for making financial transactions through the victim's bank account. Getting a new SIM card against a registered mobile number fraudulently is known as SIM Swap."
                }

                if(it.text.toString() == "Debit / Credit Card Fraud")
                {
                    txtAwarnessCyberCrimeCategory.text = "Credit card (or debit card) fraud involves the unauthorized use of another's credit or debit card information for the purpose of purchases or withdrawing funds from it."
                }

            }
        }

        chipGroupOptions.setOnCheckedChangeListener { group, checkedId: Int ->
            // Get the checked chip instance from chip group
            val chip: Chip? = findViewById(checkedId)

            chip?.let {
                // Show the checked chip text on toast message
                Toast.makeText(applicationContext,it.text, Toast.LENGTH_SHORT).show()
                if(it.text.toString() == "Safety Measures")
                {
                    val intent = Intent(this,CyberCrimeSafetyMeasures::class.java)
                    startActivity(intent)
                    overridePendingTransition(
                        R.anim.fade_in,
                        R.anim.fade_out
                    )

                }
                if(it.text.toString() == "Emergency Call")
                {
                    val u: Uri = Uri.parse("tel:" + "181")
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
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )

    }
}
