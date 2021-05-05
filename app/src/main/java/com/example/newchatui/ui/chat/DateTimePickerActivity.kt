package com.example.newchatui.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.DatePicker
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.newchatui.R
import kotlinx.android.synthetic.main.activity_date_time_picker.*
import kotlinx.android.synthetic.main.activity_date_time_picker.botLogo
import java.util.*

class DateTimePickerActivity : AppCompatActivity(){

    private var msg:  String = ""
    var sharedName : String = "sharedPreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_time_picker)
        progress_bar_date.animateProgress(2000,60,90)
        Glide.with(this).asGif().load(R.raw.logo).into(botLogo)

            val datePicker = findViewById<DatePicker>(R.id.datePicker)
            val today = Calendar.getInstance()
            datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)

            ) { view, year, month, day ->
                val month = month + 1
                msg =  "$day/$month/$year"
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }

            btnDate.setOnClickListener {
                if(msg!=""){
                    val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName,0)
                    val editor = sharedPreferences.edit()
                    editor.putString("date",msg)
                    editor.commit()
                    var i = Intent(applicationContext, ComplaintDetailsActivity::class.java)
                    startActivity(i)
                    overridePendingTransition(
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                }
                else if(msg==""){
                    Toast.makeText(applicationContext,"Please choose date",Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

}
