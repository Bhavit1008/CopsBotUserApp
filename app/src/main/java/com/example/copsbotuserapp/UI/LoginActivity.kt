package com.example.copsbotuserapp.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.copsbotuserapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //login button later API Call.
        btn_login.setOnClickListener {
            Toast.makeText(this,"Login and redirect to mainActivity",Toast.LENGTH_SHORT).show()
        }

        //registration link create account section.
        registration_activity_bypass.setOnClickListener {
            Toast.makeText(this,"Redirect to Registration activity",Toast.LENGTH_SHORT).show()
        }

    }
}
