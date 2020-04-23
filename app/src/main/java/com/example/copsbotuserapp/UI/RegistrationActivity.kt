package com.example.copsbotuserapp.UI

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.copsbotuserapp.API.RetrofitClient
import com.example.copsbotuserapp.models.RegistrationResponse
import com.example.copsbotuserapp.R
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_PHONE_STATE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 1)
        }

        val IMEI1 = tm.imei
        val slot = tm.dataState
        val operatorName = tm.simOperatorName

        IMEI.text = IMEI1
        when(slot){
            1->{SLOT.text = "DATA_DISCONNECTED"}
            2->{SLOT.text = "DATA_CONNECTING"}
            3->{SLOT.text ="DATA_CONNECTED"}
            4->{SLOT.text = "DATA_SUSPENDED"}
        }
        SIM.text = operatorName

        btn_register.setOnClickListener {
            RetrofitClient.instance.createUser(
                "Bhavit",
                "7597917007",
                IMEI1,
                "24.576952",
                "74.257894",
                "password"
            ).enqueue(object :Callback<RegistrationResponse>{
                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    Toast.makeText(applicationContext,"failed" + t.message,Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                    Toast.makeText(applicationContext,"success" +response.body().toString(),Toast.LENGTH_SHORT).show()
                    Log.d("reg",response.body().toString())
                }

            })
        }
    }
}
