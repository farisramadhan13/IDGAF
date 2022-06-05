package com.capstoneproject.aplikasiantifoodwaste.scan

import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.capstoneproject.aplikasiantifoodwaste.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodScanViewModel: ViewModel() {
    fun scan(base64: String){
        val service = ApiConfig.getApiService().scan(base64)
        service.enqueue(object: Callback<FoodScanResponse>{
            override fun onResponse(
                call: Call<FoodScanResponse>,
                response: Response<FoodScanResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        Log.e("Food Scan Activity", "onSuccess")
                    }
                } else{
                    Log.e("Food Scan Activity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FoodScanResponse>, t: Throwable) {
                Log.e("Food Scan Activity", "onFailure: ${t.message}")
            }
        })
    }
}