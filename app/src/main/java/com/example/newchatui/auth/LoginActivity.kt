package com.example.newchatui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.newchatui.R
import com.example.newchatui.api.RetrofitApi
import com.example.newchatui.api.RetrofitClient
import com.example.newchatui.model.LoginResponse
import com.example.newchatui.ui.chat.CrimeReportActivity
import com.example.newchatui.ui.DashBoardActivity
import com.example.newchatui.ui.chat.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    var number :String = ""
    var password :String = ""
    var sharedName : String = "sharedPreference"
    var token :String = ""
    var userId :String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val languages = resources.getStringArray(R.array.Languages)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                   if(languages[position] == "Gujrati" || languages[position] == "Punjabi"){
                       Toast.makeText(applicationContext,"Comming Soon",Toast.LENGTH_SHORT).show()
                   }
                    val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName,0)
                    val editor = sharedPreferences.edit()
                    editor.putString("language",languages[position])
                    editor.commit()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName,0)
                    val editor = sharedPreferences.edit()
                    editor.putString("language",languages[0])
                    editor.commit()
                }
            }
        }



        btn_login.setOnClickListener {
            number = edt_number_login.text.toString()
            password = edt_password_login.text.toString()
            if(number!="" && password!=""){

                RetrofitClient.instance.postLogin(
                    number,
                    password
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
                        Log.d("error",t.toString())
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName,0)
                        val editor = sharedPreferences.edit()
                        editor.clear()
                        editor.putString("token",response.body()?.token)
                        editor.putString("userId",response.body()?.userId)
                        editor.commit()
                        if(response.body()?.status == "200"){
                            Log.d("TOKEN",response.body()?.token)
                            val i = Intent(applicationContext,DashBoardActivity::class.java)
                            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(i)
                            finish()
                        }
                    }

                })

            }
            else
                Toast.makeText(applicationContext,"all fields are mendatory",Toast.LENGTH_SHORT).show()
        }


        complaintA.setOnClickListener {
            val intent = Intent(this,CrimeReportActivity::class.java)
            startActivity(intent)
        }

        registration_activity_bypass.setOnClickListener {
            val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
