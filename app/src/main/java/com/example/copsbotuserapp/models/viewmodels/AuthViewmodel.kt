package com.example.copsbotuserapp.models.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.copsbotuserapp.API.userRepository.UserRepository

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
            val response = UserRepository().userLogin(number!!,password!!)
            authListner?.success(response)
        }
    }
}
