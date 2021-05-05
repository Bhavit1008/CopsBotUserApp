package com.example.newchatui.ui.chat

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.newchatui.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_date_loc.*
import kotlinx.android.synthetic.main.activity_date_loc.botLogo

class DateLocActivity : AppCompatActivity() , OnMapReadyCallback {
    var sharedName : String = "sharedPreference"
    var landmark:String = ""
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_loc)

        setUpMap()
        progress_bar_location.animateProgress(2000,50,60)

        Glide.with(this).asGif().load(R.raw.logo).into(botLogo)

        btnLocation.setOnClickListener {
            landmark = edtLocation.text.toString()
            if(landmark != ""){
                val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName,0)
                val editor = sharedPreferences.edit()
                editor.putString("landmark",landmark)
                editor.commit()
                var i = Intent(applicationContext, DateTimePickerActivity::class.java)
                startActivity(i)
                overridePendingTransition(
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            }
            else{
                Toast.makeText(applicationContext,"please enter location",Toast.LENGTH_SHORT)
            }
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapLocationChat) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        // 1
        mMap = p0
        mMap.isMyLocationEnabled = true

// 2
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }

        mMap.setOnMapClickListener {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it))
        }



    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }


}
