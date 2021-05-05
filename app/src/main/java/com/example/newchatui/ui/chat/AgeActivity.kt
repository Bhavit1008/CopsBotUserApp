package com.example.newchatui.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.newchatui.R
import kotlinx.android.synthetic.main.activity_age.*
import kotlinx.android.synthetic.main.activity_age.botLogo

class AgeActivity : AppCompatActivity() {
    var age:String = ""
    var sharedName : String = "sharedPreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age)
        progress_bar_age.animateProgress(2000,10,20)
        Glide.with(this).asGif().load(R.raw.logo).into(botLogo)

        val handler = Handler()

        val runnable = Runnable{
            textFieldAge.visibility = View.VISIBLE
            edtAge.visibility = View.VISIBLE
            btnAge.visibility = View.VISIBLE
            val a =
                AnimationUtils.loadAnimation(this, R.anim.view_anime)
            a.reset()
            textFieldAge.clearAnimation()
            edtAge.clearAnimation()
            btnAge.clearAnimation()
            textFieldAge.startAnimation(a)
            edtAge.startAnimation(a)
            btnAge.startAnimation(a)
        }
        handler.postDelayed(runnable,1500)

        btnAge.setOnClickListener {
            age = edtAge.text.toString()
            if(age!=""){
                val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName,0)
                val editor = sharedPreferences.edit()
                editor.putString("age",age)
                editor.commit()
                var i = Intent(applicationContext, GenderActivity::class.java)
                startActivity(i)
                overridePendingTransition(
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            }
            else{
                Toast.makeText(applicationContext,"please select some category",Toast.LENGTH_SHORT)
            }

       }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}
