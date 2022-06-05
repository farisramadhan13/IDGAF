package com.capstoneproject.aplikasiantifoodwaste.searchfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityTakeFoodConfirmationBinding

class TakeFoodConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakeFoodConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeFoodConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}