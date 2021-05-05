package com.example.newchatui.model

import com.google.gson.annotations.SerializedName

data class StatusList (
    @SerializedName("_id") var id:String,
    @SerializedName("status") var status:String,
    @SerializedName("createdAt") var createdAt:String,
    @SerializedName("complaint") var complaint:String,
    @SerializedName("victimName") var victimName:String,
    @SerializedName("region") var region:String

)