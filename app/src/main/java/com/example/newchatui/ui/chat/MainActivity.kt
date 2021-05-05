package com.example.newchatui.ui.chat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newchatui.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.botLogo


class MainActivity : AppCompatActivity() {
    var name : String = ""

    var vis :Boolean = true

    var sharedName : String = "sharedPreference"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Glide.with(this).asGif().load(R.raw.logo).into(botLogo)

            progress_bar_1.animateProgress(2000,0,10)

        val handler = Handler()

        val runnable = Runnable{
            textFieldName.visibility = View.VISIBLE
            edtName.visibility = View.VISIBLE
            btnName.visibility = View.VISIBLE
            val a =
                AnimationUtils.loadAnimation(this, R.anim.view_anime)
            a.reset()
            textFieldName.clearAnimation()
            edtName.clearAnimation()
            btnName.clearAnimation()
            textFieldName.startAnimation(a)
            edtName.startAnimation(a)
            btnName.startAnimation(a)
        }
        handler.postDelayed(runnable,0)

        btnName.setOnClickListener {
            name = edtName.text.toString()
                if(name!=null){
                    val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName,0)
                    val editor = sharedPreferences.edit()
                    editor.putString("name",name)
                    editor.commit()
                    var i = Intent(applicationContext, AgeActivity::class.java)
                    startActivity(i)
                    overridePendingTransition(
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
