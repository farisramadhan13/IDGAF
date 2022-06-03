package com.capstoneproject.aplikasiantifoodwaste.tips.kualitas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityApelBinding
import com.capstoneproject.aplikasiantifoodwaste.tips.artikel.ArtikelApelSangatSegarActivity

class ApelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.cvApelSangatSegar.setOnClickListener {
            startActivity(Intent(this, ArtikelApelSangatSegarActivity::class.java))
        }
        binding.cvApelSegar.setOnClickListener {
            Toast.makeText(this,"Fitur belum dibuat", Toast.LENGTH_SHORT).show()
        }
        binding.cvApelBasi.setOnClickListener {
            Toast.makeText(this,"Fitur belum dibuat", Toast.LENGTH_SHORT).show()
        }
    }
}