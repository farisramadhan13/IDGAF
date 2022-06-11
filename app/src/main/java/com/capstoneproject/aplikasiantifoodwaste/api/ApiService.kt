package com.capstoneproject.aplikasiantifoodwaste.api

import com.capstoneproject.aplikasiantifoodwaste.scan.FoodScanPredictionResponse
import com.capstoneproject.aplikasiantifoodwaste.scan.FoodScanResponse
import com.capstoneproject.aplikasiantifoodwaste.share.ShareFoodResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("predict")
    fun scan(
        @Field("b64") b64: String
    ): Call<FoodScanResponse>

    @GET("predict")
    fun predict(): Call<FoodScanPredictionResponse>

    /*
    @POST("tes")
    fun sharefood(): Call<ShareFoodResponse>
     */
}