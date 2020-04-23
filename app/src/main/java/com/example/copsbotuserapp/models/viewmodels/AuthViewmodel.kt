package com.example.copsbotuserapp.models.viewmodels

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.copsbotuserapp.API.RetrofitClient
import com.example.copsbotuserapp.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewmodel :ViewModel(){
    var number:String? = null
    var password:String? = null
    var authListner:AuthListner?=null

    fun onLoginBtnClick(view : View){
        if (number.isNullOrEmpty() || password.isNullOrEmpty()){
            authListner?.failure()
            return
        }
        else{
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
                    Log.d("token",token)
                }
            })
            authListner?.success()
        }
    }

}
