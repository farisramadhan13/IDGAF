package com.capstoneproject.aplikasiantifoodwaste.tips.kualitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityApelBinding

class ApelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


    }
}