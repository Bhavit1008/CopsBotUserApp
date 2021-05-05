package com.example.copsbotuserapp.API.userRepository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.copsbotuserapp.API.RetrofitClient
import com.example.copsbotuserapp.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    fun userLogin(number:String,password:String):LiveData<String>{
        val loginresponse = MutableLiveData<String>()
        RetrofitClient.instance.loginUser(
            number!!,
            password!!
        ).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("error",t.toString())
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                //Toast.makeText(applicationContext,response.toString(),Toast.LENGTH_SHORT).show()
                // Toast.makeText(applicationContext,"response", Toast.LENGTH_SHORT).show()
                Log.d("response",response.body().toString())
                val token = response.body().toString().substringAfter("token=").substringBefore(",")
                Log.d("accessToken",token)
                loginresponse.value = response.body().toString()
            }
        })
        return loginresponse
    }
}