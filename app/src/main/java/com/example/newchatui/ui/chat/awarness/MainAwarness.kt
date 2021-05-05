package com.example.newchatui.ui.chat.awarness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.newchatui.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main_awarness.*

class MainAwarness : AppCompatActivity() {

    var response :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_awarness)
        Glide.with(this).asGif().load(R.raw.logo).into(botLogo)

        var strUser: String = intent.getStringExtra("sug") // 2
        botSug.text = "I suggest you to tap on " + " " + strUser

        chipGroupCrimeAwarness.setOnCheckedChangeListener { group, checkedId: Int ->
            // Get the checked chip instance from chip group
            val chip: Chip? = findViewById(checkedId)

            chip?.let {
                // Show the checked chip text on toast message
                Toast.makeText(applicationContext,it.text, Toast.LENGTH_SHORT).show()
                if(it.text.toString() == "Cyber Crime")
                {
                    val intent = Intent(this,CyberCrimeCategory::class.java)
                    startActivity(intent)
                }
                else if(it.text.toString() == "Domestic Violence against Women")
                {
                    val intent = Intent(this,WomenSafety::class.java)
                    startActivity(intent)
                }
                else if(it.text.toString() == "Crime against Children")
                {
                    val intent = Intent(this, ChildrenSafety::class.java)
                    startActivity(intent)
                }
                else if(it.text.toString() == "Road Accidents")
                {
                    val intent = Intent(this,RoadAccident::class.java)
                    startActivity(intent)
                }
                else(it.text.toString() == "Other")
                {
                    val intent = Intent(this,CyberCrimeCategory::class.java)
                    startActivity(intent)
                }
                overridePendingTransition(
                    R.anim.fade_in,
                    R.anim.fade_out
                )

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
