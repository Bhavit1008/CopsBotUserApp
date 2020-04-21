package com.example.copsbotuserapp.API

import android.telecom.Call
import com.example.copsbotuserapp.Models.DefaultResponse
import com.example.copsbotuserapp.Models.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.security.auth.callback.Callback

interface RetrofitApi {
    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("/api/users/register")
    fun createUser(
        @Field("name") name:String,
        @Field("phoneNumber") phoneNumber:String,
        @Field("IMEI") IMEI:String,
        @Field("latitude") latitude:String,
        @Field("longitude") logitude:String,
        @Field("password") password:String
    ): retrofit2.Call<DefaultResponse>

    @FormUrlEncoded
    @POST("/api/users/login")
    fun loginUser(
        @Field("phoneNumber") phoneNumber:String,
        @Field("password") password:String
    ):retrofit2.Call<LoginResponse>
}