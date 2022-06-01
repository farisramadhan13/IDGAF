package com.capstoneproject.aplikasiantifoodwaste.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityDetailStorageBinding

class DetailStorageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}