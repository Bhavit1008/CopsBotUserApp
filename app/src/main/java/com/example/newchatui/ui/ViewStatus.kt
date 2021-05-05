package com.example.newchatui.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newchatui.R
import kotlinx.android.synthetic.main.activity_view_status.*

class ViewStatus : AppCompatActivity() {

    private var victim:String=""
    private var complaint:String=""
    private var region:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_status)
        victim = intent.getStringExtra("victim")
        complaint = intent.getStringExtra("complaint")
        region = intent.getStringExtra("region")
        if(victim!="" && complaint!="" && region!=""){
            txtStatusVictimName.text = victim
            txtStatusComplaint.text = complaint
            txtStatusRegion.text = region
        }
    }
}
