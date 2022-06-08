package com.capstoneproject.aplikasiantifoodwaste.api

import com.capstoneproject.aplikasiantifoodwaste.scan.FoodScanPredictionResponse
import com.capstoneproject.aplikasiantifoodwaste.scan.FoodScanResponse
import com.capstoneproject.aplikasiantifoodwaste.share.ShareFoodResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("tes")
    fun scan(
        @Field("b64") b64: String
    ): Call<FoodScanResponse>

    @GET("tes")
    fun predict(): Call<FoodScanPredictionResponse>

    /*
    @POST("tes")
    fun sharefood(): Call<ShareFoodResponse>
     */
}