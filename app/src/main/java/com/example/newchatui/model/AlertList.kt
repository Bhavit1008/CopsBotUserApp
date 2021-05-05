package com.example.newchatui.model

import com.google.gson.annotations.SerializedName

data class AlertList(
    @SerializedName("title") var title:String,
    @SerializedName("alertMsg") var alertMsg:String
)