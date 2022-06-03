package com.capstoneproject.aplikasiantifoodwaste.tips.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityArtikelApelSangatSegarBinding

class ArtikelApelSangatSegarActivity : AppCompatActivity() {
    private lateinit var binding : ActivityArtikelApelSangatSegarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelApelSangatSegarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


    }
}