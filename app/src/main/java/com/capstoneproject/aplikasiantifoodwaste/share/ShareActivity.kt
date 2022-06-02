package com.capstoneproject.aplikasiantifoodwaste.share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityShareBinding

class ShareActivity : AppCompatActivity() {

    private lateinit var binding : ActivityShareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

    }
}