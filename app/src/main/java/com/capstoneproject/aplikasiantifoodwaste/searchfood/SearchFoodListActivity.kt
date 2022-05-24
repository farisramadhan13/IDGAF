package com.capstoneproject.aplikasiantifoodwaste.searchfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySearchFoodListBinding
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySplashBinding

class SearchFoodListActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchFoodListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}