package com.capstoneproject.aplikasiantifoodwaste.scan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityFoodScanBinding

class FoodScanActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFoodScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodScanBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()
    }
}