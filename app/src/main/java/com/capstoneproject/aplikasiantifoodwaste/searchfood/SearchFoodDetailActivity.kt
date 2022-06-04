package com.capstoneproject.aplikasiantifoodwaste.searchfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySearchFoodDetailBinding

class SearchFoodDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}