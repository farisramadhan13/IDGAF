package com.capstoneproject.aplikasiantifoodwaste.tips

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityTipsBinding
import com.capstoneproject.aplikasiantifoodwaste.scan.SaveFoodScanActivity
import com.capstoneproject.aplikasiantifoodwaste.tips.kualitas.ApelActivity

class TipsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.gambarApelTips.setOnClickListener {
            val intent = Intent(this, ApelActivity::class.java)
            intent.putExtra("EXTRA_NAME", "Apel" )
            startActivity(intent)
        }
        binding.gambarPisangTips.setOnClickListener {
            val intent = Intent(this, ApelActivity::class.java)
            intent.putExtra("EXTRA_NAME", "Pisang" )
            startActivity(intent)
        }
        binding.gambarJerukTips.setOnClickListener {
            val intent = Intent(this, ApelActivity::class.java)
            intent.putExtra("EXTRA_NAME", "Jeruk" )
            startActivity(intent)
        }
        binding.gambarTomatTips.setOnClickListener {
            val intent = Intent(this, ApelActivity::class.java)
            intent.putExtra("EXTRA_NAME", "Tomat" )
            startActivity(intent)
        }
        binding.gambarWortelTips.setOnClickListener {
            val intent = Intent(this, ApelActivity::class.java)
            intent.putExtra("EXTRA_NAME", "Wortel" )
            startActivity(intent)
        }
        binding.gambarSayurHijauTips.setOnClickListener {
            val intent = Intent(this, ApelActivity::class.java)
            intent.putExtra("EXTRA_NAME", "SayuranHijau" )
            startActivity(intent)
        }
        binding.gambarTambahArtikel.setOnClickListener {
            startActivity(Intent(this, UploadArtikelActivity::class.java))
        }
    }
}