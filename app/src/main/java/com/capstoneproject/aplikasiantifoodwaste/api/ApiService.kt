package com.capstoneproject.aplikasiantifoodwaste.api

import com.capstoneproject.aplikasiantifoodwaste.scan.FoodScanResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("tes")
    fun scan(
        @Field("b64") b64: String
    ): Call<FoodScanResponse>

    @GET("tes")
    fun predict(
        @Path("b64") b64: String
    ): Call<FoodScanResponse>
}