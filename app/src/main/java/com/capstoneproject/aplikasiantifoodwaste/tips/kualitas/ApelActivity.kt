package com.capstoneproject.aplikasiantifoodwaste.tips.kualitas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityApelBinding
import com.capstoneproject.aplikasiantifoodwaste.tips.artikel.ArtikelApelSangatSegarActivity

class ApelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val namaBuah = intent.getStringExtra("EXTRA_NAME")

        binding.cvApelSangatSegar.setOnClickListener {
            val intent = Intent(this, ArtikelApelSangatSegarActivity::class.java)
            intent.putExtra("EXTRA_NAME", namaBuah )
            intent.putExtra("EXTRA_MATURITY", "F" )
            startActivity(intent)
        }
        binding.cvApelSegar.setOnClickListener {
            val intent = Intent(this, ArtikelApelSangatSegarActivity::class.java)
            intent.putExtra("EXTRA_NAME", namaBuah )
            intent.putExtra("EXTRA_MATURITY", "M" )
            startActivity(intent)
        }
        binding.cvApelBasi.setOnClickListener {
            val intent = Intent(this, ArtikelApelSangatSegarActivity::class.java)
            intent.putExtra("EXTRA_NAME", namaBuah )
            intent.putExtra("EXTRA_MATURITY", "R" )
            startActivity(intent)
        }
    }
}