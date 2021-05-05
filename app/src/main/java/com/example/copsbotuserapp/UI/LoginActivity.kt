package com.example.copsbotuserapp.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.copsbotuserapp.models.viewmodels.AuthListner
import com.example.copsbotuserapp.models.viewmodels.AuthViewmodel
import com.example.copsbotuserapp.R
import com.example.copsbotuserapp.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),AuthListner {

    var number:String = ""
    var password :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewmodel = ViewModelProviders.of(this).get(AuthViewmodel::class.java)
        binding.viewmodel = viewmodel
        viewmodel.authListner = this
        //login button later API Call.
        btn_login.setOnClickListener {
//            number = edt_phone.text.toString()
//            password = edt_password.text.toString()
//            if(number !=null && password !=null){
//                RetrofitClient.instance.loginUser(
//                    number,
//                    password
//                ).enqueue(object : Callback<LoginResponse> {
//                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                        Toast.makeText(applicationContext,t.toString(),Toast.LENGTH_SHORT).show()
//                        Log.d("error",t.toString())
//                    }
//
//                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                        //Toast.makeText(applicationContext,response.toString(),Toast.LENGTH_SHORT).show()
//                       // Toast.makeText(applicationContext,"response", Toast.LENGTH_SHORT).show()
//                       Toast.makeText(applicationContext,response.body().toString(),Toast.LENGTH_SHORT)
//                        Log.d("response",response.body().toString())
//                        val token = response.body().toString().substringAfter("token=").substringBefore(",")
//                        Log.d("token",token)
//                    }
//
//                })
//            }
//            else{
//                Toast.makeText(this,"NOT ENTERED",Toast.LENGTH_SHORT).show()
//            }
        }

        //registration link create account section.
        registration_activity_bypass.setOnClickListener {
            //Toast.makeText(this,"Redirect to Registration activity",Toast.LENGTH_SHORT).show()
            intent=Intent(this,RegistrationActivity::class.java)
            startActivity(intent)

        }

    }

    override fun success(response: LiveData<String>) {
        response.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            Log.d("success",it)
            val preference=getSharedPreferences("myToken", Context.MODE_PRIVATE)
            val editor=preference.edit()
            editor.putString("access",it.toString().substringAfter("token=").substringBefore(","))
            editor.commit()
            val intent = Intent(this,ChatActivity::class.java)
            startActivity(intent)
        })
    }

    override fun failure() {
        Toast.makeText(applicationContext,"failure",Toast.LENGTH_SHORT).show()
    }
}
