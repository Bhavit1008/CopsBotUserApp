package com.example.copsbotuserapp.models

data class RegistrationResponse(val error:Boolean,val message:String , val users: Restritation_Users,var token:String)