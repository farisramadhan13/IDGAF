package com.capstoneproject.aplikasiantifoodwaste.scan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityScanFoodBinding

class ScanFoodActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScanFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}