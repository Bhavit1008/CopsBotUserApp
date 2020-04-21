package com.example.copsbotuserapp.UI

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.copsbotuserapp.R
import kotlinx.android.synthetic.main.activity_registration.*


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
    }
}
