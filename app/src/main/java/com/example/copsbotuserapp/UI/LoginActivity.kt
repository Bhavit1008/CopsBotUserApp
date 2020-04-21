package com.example.copsbotuserapp.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.copsbotuserapp.API.RetrofitApi
import com.example.copsbotuserapp.API.RetrofitClient
import com.example.copsbotuserapp.Models.LoginResponse
import com.example.copsbotuserapp.R
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    var number:String = ""
    var password :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //login button later API Call.
        btn_login.setOnClickListener {
            number = edt_phone.text.toString()
            password = edt_password.text.toString()
            if(number !=null && password !=null){
                RetrofitClient.instance.loginUser(
                    "7726062540",
                    "password"
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext,t.toString(),Toast.LENGTH_SHORT).show()
                        Log.d("error",t.toString())
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        //Toast.makeText(applicationContext,response.toString(),Toast.LENGTH_SHORT).show()
                       Toast.makeText(applicationContext,response.message().toString(),Toast.LENGTH_SHORT)
                    }

                })
            }
            else{
                Toast.makeText(this,"NOT ENTERED",Toast.LENGTH_SHORT).show()
            }
        }

        //registration link create account section.
        registration_activity_bypass.setOnClickListener {
            Toast.makeText(this,"Redirect to Registration activity",Toast.LENGTH_SHORT).show()
        }

    }
}
