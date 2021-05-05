package com.example.newchatui.api

import com.example.newchatui.model.*
import com.google.android.gms.tasks.Task
import io.reactivex.Observable
import retrofit2.http.*


interface RetrofitApi {
    @FormUrlEncoded
    @POST("/api/complaints")
    fun createUser(
        @Field("authorization") authorization:String,
        @Field("victimName") victimName:String,
        @Field("complaint") complaint:String,
        @Field("crimeCategory") crimeCategory:String,
        @Field("age") age:String,
        @Field("gender") gender:String,
        @Field("iLatitude") iLatitude:String,
        @Field("iLongitude") iLongitude:String,
        @Field("landmark") landmark:String,
        @Field("dateOfIncident") dateOfIncident:String,
        @Field("timeOfIncident") timeOfIncident:String
        ): retrofit2.Call<ComplaintResponse>


    @FormUrlEncoded
    @POST("/api/incident")
    fun postImage(
        @Field("authorization") authorization:String,
        @Field("evidence") image:String,
        @Field("incidentDesc") desc:String,
        @Field("iLatitude") latitude:String,
        @Field("iLongitude") longitude:String
    ): retrofit2.Call<ImageResponse>

    @FormUrlEncoded
    @POST("/api/voiceNote")
    fun postVoiceNote(
        @Field("authorization") authorization:String,
        @Field("audio") voiceNote:String,
        @Field("iLatitude") iLatitude:String,
        @Field("iLongitude") iLongitude:String
    ): retrofit2.Call<ImageResponse>



        @GET("/api/alert")
        fun getAllAlerts(): Observable<List<AlertList>>

    @GET("/api/status")
    fun getAllStatus(@Header("Id") userId:String): Observable<List<StatusList>>

    @FormUrlEncoded
    @POST("/api/users/login")
    fun postLogin(
        @Field("phoneNumber") phoneNumber:String,
        @Field("password") password:String
    ): retrofit2.Call<LoginResponse>

    @FormUrlEncoded
    @POST("/api/users/register")
    fun postRegistration(
        @Field("name") name:String,
        @Field("phoneNumber") phoneNumber:String,
        @Field("IMEI") IMEI:String,
        @Field("latitude") latitude:String,
        @Field("longitude") longitude:String,
        @Field("password") password:String
        ): retrofit2.Call<RegistrationResponse>


}
