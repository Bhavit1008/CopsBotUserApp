package com.example.copsbotuserapp.maps

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.copsbotuserapp.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity() ,OnMapReadyCallback,GoogleMap.OnMapClickListener {

    private var mMap: GoogleMap? = null
    private var lat: String? = null
    private var lng: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        Btn_MapOk.setOnClickListener {
            Toast.makeText(applicationContext,lat + " " + lng,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.setOnMapClickListener { arg0 ->
            googleMap.addMarker(MarkerOptions().position(arg0))
            Log.d("arg0", arg0.latitude.toString() + "-" + arg0.longitude)
        }
    }

    override fun onMapClick(p0: LatLng?) {
        TODO("Not yet implemented")
    }
}
