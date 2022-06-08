package com.capstoneproject.aplikasiantifoodwaste.share

import com.google.gson.annotations.SerializedName

data class ShareFoodResponse(
    @field:SerializedName("photo")
    val photo: String,

    @field:SerializedName("name")
    val name:String,

    @field:SerializedName("description")
    val description:String,

    @field:SerializedName("stock")
    val stock:Int,

    @field:SerializedName("addressName")
    val addressName:String,

    @field:SerializedName("addressTelephone")
    val addressTelephone:String,

    @field:SerializedName("addressComplete")
    val addressComplete:String
)
