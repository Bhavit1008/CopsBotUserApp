package com.example.newchatui.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.newchatui.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_gender.*
import kotlinx.android.synthetic.main.activity_gender.botLogo

class GenderActivity : AppCompatActivity() {

    var sharedName : String = "sharedPreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)
        progress_bar_gender.animateProgress(2000,20,30)
        Glide.with(this).asGif().load(R.raw.logo).into(botLogo)

        chipGroup.setOnCheckedChangeListener { group, checkedId: Int ->
            // Get the checked chip instance from chip group
            val chip: Chip? = findViewById(checkedId)

            chip?.let {
                // Show the checked chip text on toast message
                Toast.makeText(applicationContext,it.text ,Toast.LENGTH_SHORT).show()
                val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName,0)
                val editor = sharedPreferences.edit()
                editor.putString("gender",it.text.toString())
                editor.commit()
            }


                var i = Intent(applicationContext,
                    CrimeReportActivity::class.java)
                startActivity(i)
                 overridePendingTransition(
                     R.anim.fade_in,
                     R.anim.fade_out
                 )


        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}