package com.example.newchatui.features.statusTracking

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newchatui.R
import com.example.newchatui.adapters.AlertsAdapter
import com.example.newchatui.adapters.StatusAdapter
import com.example.newchatui.api.RetrofitApi
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.activity_alerts.*
import kotlinx.android.synthetic.main.activity_status_tracking.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class StatusTrackingActivity : AppCompatActivity() {
    var sharedName: String = "sharedPreference"
    var userId :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_tracking)
        statusRecyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName, Context.MODE_PRIVATE)

        userId = sharedPreferences.getString("userId","5f0a9b2c1082d539bc7aa4b7")

        val retrofit = Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://copsbot-api.herokuapp.com").build()
        val postsApi = retrofit.create(RetrofitApi::class.java)
        var response = postsApi.getAllStatus(userId)

        response.observeOn(AndroidSchedulers.mainThread()).subscribeOn(IoScheduler()).subscribe {
            statusRecyclerView.adapter = StatusAdapter(it, this)
        }

    }
}
