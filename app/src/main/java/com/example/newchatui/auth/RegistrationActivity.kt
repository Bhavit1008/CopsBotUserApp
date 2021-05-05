package com.example.newchatui.auth

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.newchatui.R
import com.example.newchatui.api.RetrofitClient
import com.example.newchatui.model.LoginResponse
import com.example.newchatui.model.RegistrationResponse
import com.example.newchatui.ui.DashBoardActivity
import com.example.newchatui.ui.chat.MainActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationActivity : AppCompatActivity(){
    var number:String = ""
    var password:String = ""
    var name :String = ""
    var IMEI :String = ""
    var latitude:String = "23.432521"
    var longitude :String = "73.432515"



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

//        IMEI = tm.imei
        IMEI = "356938035643809"
        Toast.makeText(applicationContext,IMEI,Toast.LENGTH_SHORT).show()

        btn_register.setOnClickListener {
            name = edt_name_admin_reg.text.toString()
            password = edt_password_admin_reg.text.toString()
            number = edt_number_admin_reg.text.toString()
            if(name !="" && number !="" && password !=""){


                RetrofitClient.instance.postRegistration(
                    name,
                    number,
                    IMEI,
                    latitude,
                    longitude,
                    password
                ).enqueue(object : Callback<RegistrationResponse> {
                    override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                        Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
                        Log.d("error",t.toString())
                    }

                    override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                        Toast.makeText(applicationContext,response.body()?.token,Toast.LENGTH_SHORT).show()
                        val i = Intent(applicationContext,LoginActivity::class.java)
                        startActivity(i)
                    }

                })




            }
            else{
                Toast.makeText(applicationContext,"all fields are mendatory",Toast.LENGTH_SHORT).show()
            }
        }

    }


}

