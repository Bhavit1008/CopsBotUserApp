package com.example.copsbotuserapp.API

import com.example.copsbotuserapp.models.LoginResponse
import com.example.copsbotuserapp.models.RegistrationResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitApi {
    @FormUrlEncoded
    @POST("/api/users/register")
    fun createUser(
        @Field("name") name:String,
        @Field("phoneNumber") phoneNumber:String,
        @Field("IMEI") IMEI:String,
        @Field("latitude") latitude:String,
        @Field("longitude") logitude:String,
        @Field("password") password:String
    ): retrofit2.Call<RegistrationResponse>

    @FormUrlEncoded
    @POST("/api/users/login")
    fun loginUser(
        @Field("phoneNumber") phoneNumber:String,
        @Field("password") password:String
    ):retrofit2.Call<LoginResponse>
}