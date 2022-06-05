package com.capstoneproject.aplikasiantifoodwaste.api

import com.capstoneproject.aplikasiantifoodwaste.scan.FoodScanResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("test")
    fun scan(
        @Field("base64") base64: String
    ): Call<FoodScanResponse>
}