package com.capstoneproject.aplikasiantifoodwaste.scan

import com.google.gson.annotations.SerializedName

data class FoodScanResponse(
    @field:SerializedName("msg")
    val msg: String
)

data class FoodScanPredictionResponse(
    @field:SerializedName("predict1")
    val predict1: String
)
